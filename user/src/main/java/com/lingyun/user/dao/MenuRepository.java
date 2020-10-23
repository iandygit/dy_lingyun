package com.lingyun.user.dao;

import com.lingyun.user.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity,Long> {


    @Query("select m  from  com.lingyun.user.entity.RoleAuthEntity r left  join MenuEntity m on r.menuId=m.id where (:roleId is null or r.id=:roleId)")
    public List<MenuEntity> findAllByRoleId(@Param("roleId") Long roleId);
}
