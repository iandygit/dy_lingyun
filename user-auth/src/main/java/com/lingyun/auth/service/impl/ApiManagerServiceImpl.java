package com.lingyun.auth.service.impl;

import com.lingyun.auth.service.ApiManagerService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiManagerServiceImpl implements ApiManagerService {
    Logger logger= LoggerFactory.getLogger(ApiManagerServiceImpl.class);


    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }


    @Override
    public ResponseEntity<String> checkApiKey(String uuid, String apikey) {
        logger.info("开始验证apikey，调用服务"+uuid);
        if(StringUtils.isEmpty(uuid) ||StringUtils.isEmpty(apikey)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("uuid或者密钥不能为空");
        }
        String uuid_real=uuid.substring(5);

        Object key =redisTemplate.opsForHash().get("APIKETS",uuid_real);//

        if(null== key || ! key.equals(apikey)){

            logger.info(".........."+uuid_real);
            logger.info("开始验证apikey，客户端无权访问，key="+ key);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("客户端无权访问");
        }else {
            logger.info("开始验证apikey，客户端授权通过");
            return ResponseEntity.ok("客户端授权通过");
        }
    }


}
