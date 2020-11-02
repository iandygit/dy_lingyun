package com.lingyun.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.lingyun.user.entity.UserEntity;
import com.lingyun.user.service.UserService;
import com.lingyun.user.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "用户管理",tags = {"用户管理"},produces = MediaType.APPLICATION_JSON_VALUE)

@RestController
@RequestMapping("user")
public class UserController {
   Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    /**
     * 查询
     * @return
     */
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 401, message = "权限不足") })
    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ApiOperation(value = "用户数据列表分页", notes = "用户管理", tags = "用户管理")
    public ResponseEntity<Page<UserVo>> list(String  phoneNum, Long roleId, Integer pageNum, Integer pageSize) {
        if(null==pageSize ||pageSize ==0){
            pageSize=20;
        }
        if(null==pageNum || pageNum<=1){
            pageNum=0;
        }else {
            pageNum=pageNum-1;
        }
        if(StringUtils.isEmpty(phoneNum)){
            phoneNum=null;
        }
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable= new PageRequest(pageNum,pageSize,sort);

        logger.info("开始用户数据列表");
        Page<UserVo> list = this.userService.findallByRoleIdAdIphone(phoneNum,roleId,pageable);
        logger.info("用户数据列表结束");

        return ResponseEntity.ok(list);
    }

    /**
     * 保存用户
     * @return
     */
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 401, message = "权限不足") })
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "添加/编辑用户", notes = "用户管理", tags = "用户管理")
    public ResponseEntity<String> saveUseer(UserEntity userEntity){
        logger.info("开始操作用户");
        UserEntity user= userService.save(userEntity);
        if(null==user){
            if(null!=userEntity.getId()){//编辑
                return ResponseEntity.ok("用户更新失败，找不到合法的数据");
            }else {
                return ResponseEntity.ok("添加失败，用户名重复");
            }
        }
        logger.info("操作用户完成");
        return ResponseEntity.ok("操作成功");
    }
    @RequestMapping(value = "/{userName}",method = RequestMethod.GET)
    @ApiOperation(value = "查找用户", notes = "通过用户名查找用户", tags = "用户管理")
    public ResponseEntity<UserEntity> findByName(@PathVariable("userName") String userName){
        logger.info("这里是用户中心,获取userName: "+userName);
        UserEntity userEntity=userService.findOneByName(userName);
        return ResponseEntity.ok(userEntity);
    }

    @RequestMapping(value = "/name",method = RequestMethod.GET)
    @ApiOperation(value = "验证用户是否存在", notes = "通过用户名查找用户", tags = "用户管理")
    public ResponseEntity<String> checkUserName(String username){
        logger.info("这里是用户中心,获取userName: "+username);
        UserEntity userEntity=userService.findOneByName(username);

        if(null==userEntity ){
            ResponseEntity.ok("true");

        }
        return ResponseEntity.ok("false");
    }
    @RequestMapping(value = "/one/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "通过id查找用户", notes = "通过id查找用户", tags = "用户管理")
    public ResponseEntity<UserEntity> getUser(@PathVariable("userId") Integer userId){
        if(null==userId){
               return ResponseEntity.ok(null);
        }
        UserEntity userEntity=userService.getOne(Long.valueOf(userId));
        userEntity.setPassWord("");//不返回密码给客户端
        return ResponseEntity.ok(userEntity);
    }
    @RequestMapping(value = "validata",method = RequestMethod.POST)
    public ResponseEntity<JSONObject> validataUser(String userName, String passWord){
        logger.info("开始验证登录="+userName+"------"+passWord);
        ResponseEntity<JSONObject> userEntity=userService.findByUserNameAndPassowrd(userName,passWord);

        return userEntity;
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "通过id删除用户", notes = "通过id删除用户信息", tags = "用户管理")
    public ResponseEntity<JSONObject> deleteUser(@PathVariable("id") Integer id){
        JSONObject object=new JSONObject();
        if(null==id){
            object.put("status","201");
            object.put("msg","用户id不能为空");

        }
        userService.deleteById(Long.valueOf(id));
        object.put("status","200");
        object.put("msg","操作成功");
        return ResponseEntity.ok(object);
    }
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    @ApiOperation(value = "密码修改", notes = "通过用户id和用户名修改密码", tags = "用户管理")
    public ResponseEntity<JSONObject> updatePassword(@PathVariable("id") Integer id,String username,String password){


        JSONObject object= userService.updatePassword(id,username,password);



        return ResponseEntity.ok(object);
    }


}
