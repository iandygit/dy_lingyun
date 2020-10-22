package com.lingyun.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class HystrixCommandController {
   Logger logger= LoggerFactory.getLogger(HystrixCommandController.class);
    @RequestMapping("hystrixTimeout")
    /**
     * 单个服务断路由
     */
    public ResponseEntity<String> hystrixTimeout(){
        logger.error("触发了断路由");

        return ResponseEntity.ok("链接超时，请稍后再试");

    }

    /**
     * 断路器
     * @return
     */
    public ResponseEntity<String> authHystrixCommand(){
        logger.error("触发了短路由");
        return  ResponseEntity.ok("触发了断路器");
    }
}
