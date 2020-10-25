package com.lingyun.gateway.service;

import com.alibaba.fastjson.JSON;
import com.lingyun.gateway.model.LoginUser;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class Session {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    Long expireTime = 10800L;

    /**
     * 保存session
     * @param loginUser
     */
    public void saveSession(LoginUser loginUser) {
        String key = String.format("login:user:%s", loginUser.userToken);

        redisTemplate.opsForValue().set(key, JSON.toJSONString(loginUser),
                expireTime, TimeUnit.SECONDS);
    }

    /**
     * 获取session
     * @param token
     * @return
     */
    public LoginUser getSession(String token){
        String key = String.format("login:user:%s", token);

        String s = redisTemplate.opsForValue().get(key);
        if (Strings.isEmpty(s)){
            return null;
        }

        return JSON.parseObject(s, LoginUser.class);
    }
}