package com.lingyun.auth.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoginUserFeignFallback implements LoginUserFeign {
    @Override
    public ResponseEntity<JSONObject> validateUser(String username, String password) {

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("status","2002");
        jsonObject.put("msg","授权中心验证失败");
        return ResponseEntity.ok(jsonObject);
    }
}
