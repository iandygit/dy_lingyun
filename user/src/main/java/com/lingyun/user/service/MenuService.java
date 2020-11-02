package com.lingyun.user.service;

import com.lingyun.user.entity.MenuEntity;
import com.lingyun.user.vo.MenuVo;

import java.util.List;

public interface MenuService  extends BaseService<MenuEntity>{
    public List<MenuEntity> findAll();

    public List<MenuEntity> findAllByRoleId(Integer roleId);


}
