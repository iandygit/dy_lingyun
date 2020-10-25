package com.lingyun.user.service.impl;

import com.lingyun.user.dao.MenuRepository;
import com.lingyun.user.entity.MenuEntity;
import com.lingyun.user.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;
    @Override
    public List<MenuEntity> findAll() {


        return menuRepository.findAll();
    }

    @Override
    public List<MenuEntity> findAllByRoleId(Long roleId) {



        return menuRepository.findAllByRoleId(roleId);
    }


    @Override
    public MenuEntity save(MenuEntity menuEntity) {

        return menuRepository.saveAndFlush(menuEntity);
    }

    @Override
    public void delete(MenuEntity menuEntity) {
        menuRepository.delete(menuEntity);
    }

    @Override
    public List<MenuEntity> getAll() {
        return menuRepository.findAll();
    }

    @Override
    public Page<MenuEntity> getPage(Example<MenuEntity> example, Pageable pageable) {
        return null;
    }

    @Override
    public MenuEntity getOne(Long id) {
        Optional<MenuEntity> menuEntity=menuRepository.findById(id);
        if(null==menuEntity){
            return null;
        }
        return menuEntity.get();
    }

    @Override
    public void deleteById(Long id) {
        menuRepository.deleteById(id);
    }
}
