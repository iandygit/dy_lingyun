package com.lingyun.user.service.impl;

import com.lingyun.user.dao.RoleRepository;
import com.lingyun.user.entity.RoleEntity;
import com.lingyun.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public RoleEntity save(RoleEntity roleEntity) {
        return roleRepository.saveAndFlush(roleEntity);
    }

    @Override
    public void delete(RoleEntity roleEntity) {
        roleRepository.delete(roleEntity);
    }

    @Override
    public List<RoleEntity> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Page<RoleEntity> getPage(Example<RoleEntity> example, Pageable pageable) {


        return null;
    }

    @Override
    public RoleEntity getOne(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }
}
