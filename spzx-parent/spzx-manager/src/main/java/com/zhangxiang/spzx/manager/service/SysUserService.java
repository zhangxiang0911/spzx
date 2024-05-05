package com.zhangxiang.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.zhangxiang.spzx.model.dto.system.AssginRoleDto;
import com.zhangxiang.spzx.model.dto.system.LoginDto;
import com.zhangxiang.spzx.model.dto.system.SysUserDto;
import com.zhangxiang.spzx.model.entity.system.SysUser;
import com.zhangxiang.spzx.model.vo.system.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteById(Long userId);

    void doAssign(AssginRoleDto assginRoleDto);
}
