package com.lingyun.auth.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "API-MEMBER",fallback = LoginUserFeignFallback.class)
public interface LoginUserFeign  {
    @RequestMapping(value="/user/validata", method= RequestMethod.POST)
    public ResponseEntity<JSONObject> validateUser(@RequestParam(value = "userName") String userName, @RequestParam(value = "passWord") String passWord);

}
