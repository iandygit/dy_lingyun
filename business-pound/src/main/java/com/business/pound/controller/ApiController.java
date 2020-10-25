package com.business.pound.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.business.pound.entity.ApiEentity;
import com.business.pound.service.ApiService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("pound-api")
@Api(value = "api权限验证",tags = "磅单客户端api管理")
public class ApiController {
    Logger logger= LoggerFactory.getLogger(ApiController.class);
    @Autowired
    private ApiService apiService;
    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping(method = RequestMethod.GET)
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 401, message = "权限不足") })
    @ApiOperation(value = "获取所有授权的keys",tags = "磅单客户端api管理")
    public ResponseEntity<List<JSONObject>> getAllApiKeys(){
        boolean flag=redisTemplate.hasKey("APIKEYS");
        List<JSONObject> list=new ArrayList<>();
        if (!flag){
            List<ApiEentity> apiEentities=apiService.getAll();
            for (ApiEentity apiEentity:apiEentities){
                JSONObject object=new JSONObject();
                object.put("uuid",apiEentity.getUuid());
                object.put("key",apiEentity.getKey());
                list.add(object);
                redisTemplate.opsForHash().put("APIKEYS",apiEentity.getUuid(),apiEentity.getKey());
            }

            return ResponseEntity.ok(list);
        }else {
           // List<ApiEentity> list=redisTemplate.opsForHash().values("APIKEYS");
           // List<JSONObject> list=new ArrayList<>();
            Set<String> uuids=redisTemplate.opsForHash().keys("APIKEYS");
            redisTemplate.opsForHash().values("APIKEYS");
            Iterator it=uuids.iterator();
            while(it.hasNext()){
                String uuid= (String) it.next();
                JSONObject apiEentity=new JSONObject();
                apiEentity.put("uuid",uuid);
                apiEentity.put("key",redisTemplate.opsForHash().get("APIKEYS",uuid));

                list.add(apiEentity);
            }

            logger.info(JSONArray.toJSONString(list));
            return ResponseEntity.ok(list);
        }

    }


}
