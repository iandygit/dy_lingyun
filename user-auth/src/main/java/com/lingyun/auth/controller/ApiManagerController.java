package com.lingyun.auth.controller;

import com.lingyun.auth.service.ApiManagerService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@Api(value = "api客户端授权",tags = "api管理")
public class ApiManagerController {

    Logger logger= LoggerFactory.getLogger(ApiManagerController.class);
    @Autowired
    private ApiManagerService apiManagerService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> checkApiKey(String uuid, String apikey){

        logger.info("开始获取key");

        ResponseEntity<String> responseEntity= apiManagerService.checkApiKey(uuid,apikey);
        logger.info("获取key结束");
        return responseEntity;
    }
}
