package com.lingyun.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lingyun.user.dao.RoleAuthRepository;
import com.lingyun.user.dao.RoleRepository;
import com.lingyun.user.dao.UserRepository;
import com.lingyun.user.entity.MenuEntity;
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
import com.lingyun.user.util.StringUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
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

       String pwdmd5="";
        if(null==userEntity){
            jsonObject.put("status",5000);
            jsonObject.put("menu","");
            jsonObject.put("msg","用户名不存在");

        }else {

            String pwd=userEntity.getPassWord();
            pwdmd5 = StringUtil.md5(password+ userEntity.getUserName().toLowerCase());
            if(pwd.equals(pwdmd5)){//验证通过
                Long roleId=userEntity.getRoleId();
                List<MenuEntity> roleAuthEntities=roleAuthRepository.findAllMenuByRoleId(roleId);
                if(null!=roleAuthEntities &&roleAuthEntities.size()>0){
                    jsonObject.put("status",2000);
                    logger.info("权限id"+roleAuthEntities);
                    jsonObject.put("usreid",userEntity.getId());
                    jsonObject.put("msg","有权限，验证通过");
                    jsonObject.put("menu",roleAuthEntities);

                }else {
                    jsonObject.put("status",2001);
                    jsonObject.put("menu",null);
                    jsonObject.put("msg","没有分配菜单权限");

                }

            }else {
                jsonObject.put("status",5001);
                jsonObject.put("menu","");
                jsonObject.put("msg","密码不正确");

            }

        }
        return ResponseEntity.ok(jsonObject);
    }

    @Override
    public Page<UserVo> findallByRoleIdAdIphone(String iphoneNum, Long roleId, Pageable pageable) {


        return userRepository.findallByRoleIdAndIphoneNum(iphoneNum,roleId,pageable);
    }

    @Override
    @Transactional
    public JSONObject updatePassword(Integer id, String username, String password) {
        JSONObject object=new JSONObject();
        UserEntity userEntity=userRepository.findByIdAndUserName(Long.valueOf(id),username);
        if(null==userEntity){
               object.put("status","201");
               object.put("msg","用户不存在");

        }else {
            String pwdmd5 = StringUtil.md5(password+ username.toLowerCase());
            userEntity.setPassWord(pwdmd5);
            object.put("status","200");
            object.put("msg","密码更改成功");
        }
        return object;
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
    @Transactional
    public UserEntity save(UserEntity userEntity) {

        if(null!=userEntity.getId() && userEntity.getId()!=0 ){//编辑
            Optional<UserEntity> entity=userRepository.findById(userEntity.getId());
            if(null==entity){
                return null;
            }
            userEntity.setPassWord(entity.get().getPassWord());//密码不允许修改
            userEntity.setUserName(entity.get().getUserName());//用户名不允许修改
            return  userRepository.saveAndFlush(userEntity);
        }else{//新增

            UserEntity entity= userRepository.findByUserName(userEntity.getUserName());
            //用户名不存在
            if(null==entity ){//可以插入
                String pwdmd5 = StringUtil.md5(userEntity.getPassWord()+ userEntity.getUserName().toLowerCase());
                userEntity.setPassWord(pwdmd5);
                return  userRepository.save(userEntity);
            }else {
                return  null;
            }
        }
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
        if(null==userEntity){
            return null;
        }

        return userEntity.get();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
