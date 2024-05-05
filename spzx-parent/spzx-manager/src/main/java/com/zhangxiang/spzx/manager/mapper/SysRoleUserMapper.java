package com.zhangxiang.spzx.manager.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRoleUserMapper {
    void deleteByUserId(Long userId);

    void dodoAssign(Long userId, Long roleId);
}
