package com.lingyun.user.dao;

import com.lingyun.user.entity.MenuEntity;
import com.lingyun.user.entity.RoleAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleAuthRepository  extends JpaRepository<RoleAuthEntity,Long> {

    @Query("select  m from RoleAuthEntity au left  join MenuEntity m on au.menuId=m.id where au.roleId=:roleId")
    public List<MenuEntity> findAllMenuByRoleId(@Param("roleId")Long roleId);

}
