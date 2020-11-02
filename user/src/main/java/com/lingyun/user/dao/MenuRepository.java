package com.lingyun.user.dao;

import com.lingyun.user.entity.MenuEntity;
import com.lingyun.user.vo.MenuVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity,Long> {


    @Query("select new com.lingyun.user.vo.MenuVo(m.id,m.menuName,m.code,m.displyOrder,m.menuUrl,m.status)  from   RoleAuthEntity r left  join MenuEntity m on r.menuId=m.id where (:roleId is null or r.roleId=:roleId)")
    public List<MenuVo> findAllAuthByRoleId(@Param("roleId") Long roleId);

    @Query("select m  from   RoleAuthEntity r left  join MenuEntity m on r.menuId=m.id where  1=1  and(:roleId is null or r.roleId=:roleId)")
    public List<MenuEntity> findMenusAuth(@Param("roleId") Long roleId);



}
