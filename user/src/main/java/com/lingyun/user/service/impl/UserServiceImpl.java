package com.lingyun.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.lingyun.user.dao.RoleAuthRepository;
import com.lingyun.user.dao.RoleRepository;
import com.lingyun.user.dao.UserRepository;
import com.lingyun.user.entity.MenuEntity;
import com.lingyun.user.entity.RoleAuthEntity;
import com.lingyun.user.entity.UserEntity;
import com.lingyun.user.service.UserService;
import com.lingyun.user.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleAuthRepository roleAuthRepository;
    /**
     * 实体管理对象
     */
    @Autowired
    EntityManager entityManager;
    @Override
    public Page<UserEntity> findPage(String name, Long roleId, Pageable pageable) {
        if(StringUtils.isEmpty(name)){
            return userRepository.findAll(pageable);
        }
        Page<UserEntity> userPage=userRepository.findAllByUserNameAndRoleId(name,roleId,pageable);
        return userPage;
    }

    public Page<UserEntity> findPage(String name, Pageable pageable){
        if(StringUtils.isEmpty(name)){
           return userRepository.findAll(pageable);
        }
        Page<UserEntity> userPage=userRepository.findAllByUserName(name,pageable);
        return userPage;
    }

    @Override
    public UserEntity findOneByName(String username) {

        return  userRepository.findByUserName(username);
    }

    @Override
    public ResponseEntity<JSONObject> findByUserNameAndPassowrd(String username, String password) {
        UserEntity userEntity=userRepository.findByUserName(username);
        JSONObject jsonObject=new JSONObject();


        if(null==userEntity){
            jsonObject.put("status",5000);
            jsonObject.put("menu","");
            jsonObject.put("msg","用户名不存在");
            return ResponseEntity.ok(jsonObject);
        }else {
            jsonObject.put("status",2000);
            String pwd=userEntity.getPassWord();
            if(pwd.equals(password)){//验证通过
                Long roleId=userEntity.getRoleId();
                List<MenuEntity> roleAuthEntities=roleAuthRepository.findAllMenuByRoleId(roleId);
                logger.info("权限id"+roleAuthEntities);
                jsonObject.put("menu",roleAuthEntities);
                return ResponseEntity.ok(jsonObject);
            }else {
                jsonObject.put("status",5001);
                jsonObject.put("menu","");
                jsonObject.put("msg","密码不正确");
                return ResponseEntity.ok(jsonObject);
            }
        }
    }

    @Override
    public Page<UserVo> findallByRoleIdAdIphone(String iphoneNum, Long roleId, Pageable pageable) {


        return userRepository.findallByRoleIdAndIphoneNum(iphoneNum,roleId,pageable);
    }
    /**
     * 给hql参数设置值
     * @param query 查询
     * @param params 参数
     */
    private void setParameters(Query query,Map<String,Object> params){
        for(Map.Entry<String,Object> entry:params.entrySet()){
            query.setParameter(entry.getKey(),entry.getValue());
        }
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return  userRepository.saveAndFlush(userEntity);
    }

    @Override
    public void delete(UserEntity userEntity) {
        userRepository.delete(userEntity);
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<UserEntity> getPage(Example<UserEntity> example, Pageable pageable) {
        return null;
    }

    @Override
    public UserEntity getOne(Long id) {
        Optional<UserEntity> userEntity=userRepository.findById(id);


        return userEntity.get();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
