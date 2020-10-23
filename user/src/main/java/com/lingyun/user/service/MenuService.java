package com.lingyun.user.service;

import com.lingyun.user.entity.MenuEntity;

import java.util.List;

public interface MenuService  extends BaseService<MenuEntity>{
    public List<MenuEntity> findAll();

    public List<MenuEntity> findAllByRoleId(Long roleId);
}
