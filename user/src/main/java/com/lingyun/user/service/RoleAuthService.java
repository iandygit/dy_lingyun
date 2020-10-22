package com.lingyun.user.service;

import com.lingyun.user.entity.MenuEntity;
import com.lingyun.user.entity.RoleAuthEntity;

import java.util.List;

public interface RoleAuthService extends BaseService<RoleAuthEntity> {
    public List<MenuEntity> findAllMenuByRoleId(Long roleId);


    public String savRoleAuth(String roleId,String menuIds);
}
