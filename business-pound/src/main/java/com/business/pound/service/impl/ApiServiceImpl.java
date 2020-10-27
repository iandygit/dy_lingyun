package com.business.pound.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.business.pound.entity.ApiEentity;
import com.business.pound.repository.ApiRepository;
import com.business.pound.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApiServiceImpl implements ApiService {

    Logger logger= LoggerFactory.getLogger(ApiServiceImpl.class);

    @Autowired
    private ApiRepository apiRepository;
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
    public ApiEentity save(ApiEentity apiEentity) {
        return apiRepository.save(apiEentity);
    }

    @Override
    public void delete(ApiEentity apiEentity) {
        apiRepository.delete(apiEentity);
    }

    @Override
    public List<ApiEentity> getAll() {
        return apiRepository.findAll();
    }

    @Override
    public Page<ApiEentity> getPage(Example<ApiEentity> example, Pageable pageable) {
        return apiRepository.findAll(example,pageable);
    }

    @Override
    public ApiEentity getOne(Long id) {
        return apiRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        apiRepository.deleteById(id);
    }

    @Override
    public List<JSONObject> getAllApiKeys() {


        List<JSONObject> list=new ArrayList<>();

         List<ApiEentity> apiEentities=apiRepository.findAll();
         //redisTemplate.multi();
         //redisTemplate.delete("APIKETS");
         for (ApiEentity apiEentity:apiEentities){
                JSONObject object=new JSONObject();
                object.put("uuid",apiEentity.getUuid());
                object.put("key",apiEentity.getKey());
                list.add(object);
                redisTemplate.opsForHash().put("APIKETS",apiEentity.getUuid(),apiEentity.getKey());
         }
        //redisTemplate.exec();
         return list;

    }
}
