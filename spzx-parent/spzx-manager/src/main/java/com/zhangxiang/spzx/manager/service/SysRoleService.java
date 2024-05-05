package com.zhangxiang.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.zhangxiang.spzx.model.dto.system.SysRoleDto;
import com.zhangxiang.spzx.model.entity.system.SysRole;

import java.util.Map;

public interface SysRoleService {
    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteById(Long roleId);

    Map<String, Object> findAllRoles();
}
