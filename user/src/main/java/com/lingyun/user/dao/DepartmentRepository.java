package com.lingyun.user.dao;

import com.lingyun.user.entity.DepartmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository  extends JpaRepository<DepartmentEntity,Long> {

    @Override
    Page<DepartmentEntity> findAll(Pageable pageable);


     Page<DepartmentEntity> findAllByDepName(String depName, Pageable pageable);
}
