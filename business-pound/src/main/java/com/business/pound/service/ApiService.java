package com.business.pound.service;

import com.alibaba.fastjson.JSONObject;
import com.business.pound.entity.ApiEentity;

import java.util.List;

public interface ApiService  extends BaseService<ApiEentity> {

    List<JSONObject> getAllApiKeys();

}
