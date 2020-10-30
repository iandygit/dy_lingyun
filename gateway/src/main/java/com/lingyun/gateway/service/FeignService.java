package com.lingyun.gateway.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "AUTHSERVER",fallback = FeignServiceFallback.class)
public interface FeignService {
    @RequestMapping(value="/api", method= RequestMethod.GET)
    public ResponseEntity<JSONObject> checkApiKeys(@RequestParam(value = "uuid")String uuid, @RequestParam(value = "apikey")String apikey);



}
