package com.zhangxiang.spzx.manager.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.zhangxiang.spzx.common.exception.ZxException;
import com.zhangxiang.spzx.manager.service.SysRoleService;
import com.zhangxiang.spzx.manager.service.SysUserService;
import com.zhangxiang.spzx.manager.service.ValidateCodeService;
import com.zhangxiang.spzx.model.dto.system.LoginDto;
import com.zhangxiang.spzx.model.dto.system.SysRoleDto;
import com.zhangxiang.spzx.model.entity.system.SysRole;
import com.zhangxiang.spzx.model.entity.system.SysUser;
import com.zhangxiang.spzx.model.vo.common.Result;
import com.zhangxiang.spzx.model.vo.common.ResultCodeEnum;
import com.zhangxiang.spzx.model.vo.system.LoginVo;
import com.zhangxiang.spzx.model.vo.system.ValidateCodeVo;
import com.zhangxiang.spzx.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
//@CrossOrigin(allowCredentials = "true" , originPatterns = "*" , allowedHeaders = "*")
public class IndexController {

    @Autowired
    private SysUserService sysUserService ;
    @Autowired
    private ValidateCodeService validateCodeService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private SysRoleService sysRoleService;


    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode(){
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping(value = "/login")
    @Operation(summary = "登录的方法")
    public Result login(@RequestBody LoginDto loginDto){
        String captcha = loginDto.getCaptcha();
        String codeKey = loginDto.getCodeKey();
        String redisCode = redisTemplate.opsForValue().get("user:login:validatecode:" + codeKey);
        if (StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode, captcha)){
            throw new ZxException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        redisTemplate.delete("user:login:validatecode:" + codeKey) ;
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping(value = "/getUserInfo")
    public Result getUserInfo(){
        return Result.build(AuthContextUtil.get() , ResultCodeEnum.SUCCESS) ;
    }

    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(value = "token") String token) {
        sysUserService.logout(token) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
}
