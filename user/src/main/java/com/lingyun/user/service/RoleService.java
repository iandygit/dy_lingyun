package com.lingyun.user.service;

import com.lingyun.user.entity.RoleEntity;

import java.util.List;

public interface RoleService extends  BaseService<RoleEntity> {

    public List<RoleEntity> findAll();



}
