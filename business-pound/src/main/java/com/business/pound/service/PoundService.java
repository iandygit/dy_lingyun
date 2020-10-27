package com.business.pound.service;


import com.alibaba.fastjson.JSONObject;
import com.business.pound.entity.PoundEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface PoundService extends  BaseService<PoundEntity> {


    public  List<PoundEntity> findAllByPoundNum(String poundNum);

    public List<PoundEntity>  findAllByPoundAccount(String poundAccount);
}
