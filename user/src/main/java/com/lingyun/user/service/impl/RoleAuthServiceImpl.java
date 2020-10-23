package com.lingyun.user.service.impl;

import com.lingyun.user.dao.RoleAuthRepository;
import com.lingyun.user.entity.MenuEntity;
import com.lingyun.user.entity.RoleAuthEntity;
import com.lingyun.user.service.RoleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleAuthServiceImpl implements RoleAuthService {
    @Autowired
    private RoleAuthRepository roleAuthRepository;

    @Override
    public RoleAuthEntity save(RoleAuthEntity roleAuthEntity) {
        return roleAuthRepository.saveAndFlush(roleAuthEntity);
    }

    @Override
    public void delete(RoleAuthEntity roleAuthEntity) {
        roleAuthRepository.delete(roleAuthEntity);
    }

    @Override
    public List<RoleAuthEntity> getAll() {
        return roleAuthRepository.findAll();
    }

    @Override
    public Page<RoleAuthEntity> getPage(Example<RoleAuthEntity> example, Pageable pageable) {
        return null;
    }

    @Override
    public RoleAuthEntity getOne(Long id) {

        Optional<RoleAuthEntity> menuEntity=roleAuthRepository.findById(id);
        if(null==menuEntity){
            return null;
        }
        return menuEntity.get();
    }

    @Override
    public void deleteById(Long id) {
        roleAuthRepository.deleteById(id);
    }

    @Override
    public List<MenuEntity> findAllMenuByRoleId(Long roleId) {
        return roleAuthRepository.findAllMenuByRoleId(roleId);
    }

    @Override
    public String savRoleAuth(String roleId, String menuIds) {

        String menuId[]=menuIds.split(",");

        for(int i=0;i<menuId.length;i++){
            RoleAuthEntity roleAuthEntity=new  RoleAuthEntity();
            roleAuthEntity.setRoleId(Long.valueOf(roleId));
            roleAuthEntity.setMenuId(Long.valueOf(menuId[i]));
            roleAuthRepository.save(roleAuthEntity);
        }
        return "操作成功";
    }
}
