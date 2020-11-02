package com.lingyun.user.service;

import com.lingyun.user.entity.DepartmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService  extends BaseService<DepartmentEntity>{


    public List<DepartmentEntity> findAll();

    public DepartmentEntity save(DepartmentEntity departmentEntity);

    public Page<DepartmentEntity> findPageByDepName(String pageName, Pageable pageable);


}
