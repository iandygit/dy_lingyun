package com.lingyun.user.service.impl;

import com.lingyun.user.dao.DepartmentRepository;
import com.lingyun.user.entity.DepartmentEntity;
import com.lingyun.user.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;


    @Override
    public List<DepartmentEntity> findAll() {

        return departmentRepository.findAll();
    }

    @Override
    public DepartmentEntity save(DepartmentEntity departmentEntity) {

        return departmentRepository.saveAndFlush(departmentEntity);
    }

    @Override
    public void delete(DepartmentEntity departmentEntity) {
        departmentRepository.delete(departmentEntity);
    }

    @Override
    public List<DepartmentEntity> getAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Page<DepartmentEntity> getPage(Example<DepartmentEntity> example, Pageable pageable) {
        return null;
    }

    @Override
    public DepartmentEntity getOne(Long id) {
        Optional<DepartmentEntity> departmentEntity=departmentRepository.findById(id);
        if(null==departmentEntity){
            return null;
        }
        return departmentEntity.get();
    }

    @Override
    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Page<DepartmentEntity> findPageByDepName(String depName, Pageable pageable) {
        if(StringUtils.isEmpty(depName)){
            return departmentRepository.findAll(pageable);
        }
        return departmentRepository.findAllByDepName(depName,pageable);
    }
}
