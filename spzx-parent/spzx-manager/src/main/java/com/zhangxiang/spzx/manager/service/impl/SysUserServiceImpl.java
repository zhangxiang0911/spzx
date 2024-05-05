package com.zhangxiang.spzx.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangxiang.spzx.common.exception.ZxException;
import com.zhangxiang.spzx.manager.mapper.SysRoleUserMapper;
import com.zhangxiang.spzx.manager.mapper.SysUserMapper;
import com.zhangxiang.spzx.manager.service.SysUserService;
import com.zhangxiang.spzx.model.dto.system.AssginRoleDto;
import com.zhangxiang.spzx.model.dto.system.LoginDto;
import com.zhangxiang.spzx.model.dto.system.SysUserDto;
import com.zhangxiang.spzx.model.entity.system.SysUser;
import com.zhangxiang.spzx.model.vo.common.ResultCodeEnum;
import com.zhangxiang.spzx.model.vo.system.LoginVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;


import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper ;
    @Override
    public LoginVo login(LoginDto loginDto) {
        String userName = loginDto.getUserName();
        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(userName);
        if (sysUser == null){
            throw new ZxException(ResultCodeEnum.LOGIN_ERROR);
        }
        String md5InputPassword = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        if (!md5InputPassword.equals(sysUser.getPassword())){
            throw new ZxException(ResultCodeEnum.LOGIN_ERROR);
        }
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:login:" + token , JSON.toJSONString(sysUser) , 30 , TimeUnit.MINUTES);
        // 构建响应结果对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login:" + token);
        return JSON.parseObject(userJson, SysUser.class);
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login:" + token) ;
    }

    @Override
    public PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum , pageSize);
        List<SysUser> sysUserList = sysUserMapper.findByPage(sysUserDto);
        PageInfo pageInfo = new PageInfo(sysUserList);
        return pageInfo;
    }

    @Override
    public void saveSysUser(SysUser sysUser) {
        SysUser dbSysUser = sysUserMapper.findByUserName(sysUser.getUserName());
        if(dbSysUser != null) {
            throw new ZxException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        // 对密码进行加密
        String password = sysUser.getPassword();
        String digestPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        sysUser.setPassword(digestPassword);
        sysUser.setStatus(0);
        sysUserMapper.saveSysUser(sysUser);
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.updateSysUser(sysUser);
    }

    @Override
    public void deleteById(Long userId) {
        sysUserMapper.deleteById(userId);
    }

    @Transactional
    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId()) ;

        // 分配新的角色数据
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        roleIdList.forEach(roleId->{
            sysRoleUserMapper.dodoAssign(assginRoleDto.getUserId(),roleId);
        });
    }


}
