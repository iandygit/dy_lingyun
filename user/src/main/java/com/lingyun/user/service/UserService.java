package com.lingyun.user.service;

import com.alibaba.fastjson.JSONObject;
import com.lingyun.user.entity.UserEntity;
import com.lingyun.user.vo.UserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface UserService extends  BaseService<UserEntity>{

    public Page<UserEntity> findPage(String name,Long roleId, Pageable pageable);
    public Page<UserEntity> findPage(String name, Pageable pageable);

    public UserEntity findOneByName(String username);

    public ResponseEntity<JSONObject> findByUserNameAndPassowrd(String username, String password);

    public Page<UserVo>  findallByRoleIdAdIphone(String iphoneNum, Long roleId, Pageable pageable);


}
