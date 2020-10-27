package com.business.pound.controller;


import com.alibaba.fastjson.JSONObject;
import com.business.pound.service.ApiService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


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


    @RequestMapping(method = RequestMethod.GET)
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 401, message = "权限不足") })
    @ApiOperation(value = "获取所有授权的keys",tags = "磅单客户端api管理")
    public ResponseEntity<List<JSONObject>> getAllApiKeys(){
        logger.info("开始加载keys 数据");
        List<JSONObject> list=  apiService.getAllApiKeys();
        logger.info("结束加载keys 数据");
        return ResponseEntity.ok(list);

    }



}
