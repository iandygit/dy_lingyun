package com.lingyun.gateway.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FeignServiceFallback implements FeignService {
    @Override
    public ResponseEntity<JSONObject> checkApiKeys(String uuid, String apikey){

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("status","2002");
        jsonObject.put("msg","授权中心验证失败");
        return ResponseEntity.ok(jsonObject);
    }
}
