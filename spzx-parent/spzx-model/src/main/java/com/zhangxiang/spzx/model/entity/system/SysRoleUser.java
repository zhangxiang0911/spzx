package com.zhangxiang.spzx.model.entity.system;

import com.zhangxiang.spzx.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class SysRoleUser extends BaseEntity {

    private Long roleId;       // 角色id
    private Long userId;       // 用户id

}
