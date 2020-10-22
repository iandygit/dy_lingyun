package com.lingyun.user.dao;

import com.lingyun.user.entity.UserEntity;
import com.lingyun.user.vo.UserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity,Long> {


    public Page findAllByUserName(String username, Pageable pageable);

    public Page<UserEntity> findAllByUserNameAndRoleId(String userName,Long roleId,Pageable pageable);

    public UserEntity findByUserName(String userName);

    @Query(value = "select  new  com.lingyun.user.vo.UserVo(user.id,user.userName,user.iphone,user.zhiwei,de.depName,ro.name) from UserEntity user inner join com.lingyun.user.entity.DepartmentEntity de on user.departId=de.id inner  join com.lingyun.user.entity.RoleEntity ro   on   user.roleId=ro.id where ( :roleId is  null  or ro.id=:roleId ) and (:phoneNum is  null or user.iphone=:phoneNum ) order by :#{#pageable}")
    public  Page<UserVo> findallByRoleIdAndIphoneNum(@Param("phoneNum") String phoneNum,@Param("roleId") Long roleId,Pageable pageable);

    //@Query(value = "select t.id,t.user_name,t.iphone,t.zhiwei,d.dep_name,r.name from t_user t  inner join t_department d on t.depart_id=d.id inner join t_role r   on   t.role_id=r.id where if(:roleId is not null  ,r.id=:roleId,1=1) and if(:phoneNum is not null,t.iphone=:phoneNum ,1=1)",nativeQuery = true)
    //public  Page<UserVo> findallByRoleIdAndIphone(@Param("phoneNum") String phoneNum,@Param("roleId") Long roleId,Pageable pageable);


}
