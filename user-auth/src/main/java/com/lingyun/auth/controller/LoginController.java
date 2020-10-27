package com.lingyun.auth.controller;


import com.alibaba.fastjson.JSONObject;
import com.lingyun.auth.service.LoginUserFeign;
import com.lingyun.auth.utils.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.concurrent.TimeUnit;

@RestController
@Api(value = "登录管理",tags = "登录管理")
public class LoginController {
    Logger logger= LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private  RedisTemplate redisTemplate;

    @Autowired
     private LoginUserFeign loginUserFeign;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "用户登录",tags = "登录管理")
    public ResponseEntity<JSONObject> login(HttpServletRequest request, String userName, String passWord, String chaptcode,String uuid){

        logger.info("验证是否否正确..."+chaptcode);
        ResponseEntity<JSONObject> responseEntity=loginUserFeign.validateUser(userName,passWord);
        JSONObject object=responseEntity.getBody();



        String status=object.getString("status");

        Object registValidateCode = redisTemplate.opsForValue().get(uuid);
        logger.info("redis中的验证码为："+registValidateCode);
        JSONObject jsonObject=new JSONObject();
        if(null==registValidateCode){

             jsonObject.put("status","5002");
             jsonObject.put("msg","验证码为空");
             return ResponseEntity.ok(jsonObject);
        }else if(!chaptcode.equals(registValidateCode.toString())){
             jsonObject.put("status","5003");
             jsonObject.put("msg","验证码不匹配");
             return ResponseEntity.ok(jsonObject);
        }else if("2001".equals(status)){

            jsonObject.put("status","2001");
            jsonObject.put("msg","没有分配权限");
            return ResponseEntity.ok(jsonObject);
        }else{
            logger.info("开始验证用户..."+userName);


            boolean falg=redisTemplate.hasKey(userName+"_role"); //判断key是否存在
            if(!falg){//不存在则，set

                //权限放入redis中
                redisTemplate.opsForValue().set(userName+"_role",responseEntity.getBody());
            }
            //ValueOperations operations=redisTemplate.opsForValue();

            if("2000".equals(status)){//验证通过，
                logger.info("有菜单权限.......");
                //生成token
                String token = JwtTokenUtil.createJWT(userName, object.getString("menu"), null);

                //String token = JWTUtil.generateToken(userName);
                //生成refreshToken
                //String UUID = OrderCodeFactory.getCommonCode(null);

                redisTemplate.delete(userName+"_token");


                //token数据放入redis
                redisTemplate.opsForHash().put(userName+"_token", "token", token);
                redisTemplate.opsForHash().put(userName+"_token", "userName", userName);
                //设置token的过期时间
                redisTemplate.expire(userName+"_token", JwtTokenUtil.JWT_TTL, TimeUnit.MILLISECONDS);
                object.put("menus",object.getString("menu"));
                object.put("token",token);
                object.put("refreshToken",userName+"_token");

                //operations.set(userName,responseEntity);
                //redisTemplate.expire(userName,30, TimeUnit.MINUTES);
            }
            return  ResponseEntity.ok(object);

        }

    }
    @RequestMapping(value = "/logOut",method = RequestMethod.GET)
    @ApiOperation(value = "用户退出",tags = "登录管理")
    public ResponseEntity<String> logOut(String userName){

        redisTemplate.delete(userName+"_token");

        return ResponseEntity.ok("退出成功！");
    }


   /* *//**
     * 刷新token
     *//*
    @GetMapping("/refreshToken")
    public ResponseEntity<String>  refreshToken(@RequestParam String refreshToken) {

        String username = (String)redisTemplate.opsForHash().get(refreshToken, "userName");
        if(StringUtils.isEmpty(username)){
            return ResponseEntity.ok("刷新token失败");
        }

        //生成新的token
        String newToken = JWTUtil.generateToken(username);

        redisTemplate.opsForHash().put(refreshToken, "token", newToken);

        JSONObject object=new JSONObject();
        object.put("newToken",newToken);
        object.put("refreshToken",refreshToken);
        return ResponseEntity.ok(object.toJSONString());
    }*/
    @GetMapping("/")
    public String index() {
        return "AUTHSERVER: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
