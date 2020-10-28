package com.lingyun.user.service.impl;

import com.lingyun.user.dao.RoleAuthRepository;
import com.lingyun.user.entity.MenuEntity;
import com.lingyun.user.entity.RoleAuthEntity;
import com.lingyun.user.service.RoleAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleAuthServiceImpl implements RoleAuthService {
    Logger logger= LoggerFactory.getLogger(RoleAuthServiceImpl.class);
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
        return roleAuthRepository.findMenusAuth(roleId);
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

    @Override
    public int deleteByRoleIdAndMenuId(Long roleId, Long menuId) {

         try {
             roleAuthRepository.deleteAllByRoleIdAndMenuId(roleId,menuId);

             return 1;
         }catch (Exception e){
             logger.error("删除角色菜单关联关系失败"+e.getMessage());
             return 0;
         }

    }
}
