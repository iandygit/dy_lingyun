package com.business.pound.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.business.pound.entity.PoundEntity;

import java.util.List;
import java.util.Map;

public interface StatisAnalySisService {

    /**
     * 货物流动总重量占比统计分析图
     * @param day
     * @param poundNum
     * @return
     */
    public List<Object[]> getWeightAnalySis(Integer day, String poundNum);

    /**
     * 货物发出公司占比统计分析图
     * @param poundNum
     * @return
     */
    public JSONArray getDeliverUnitAnalysis(String poundNum);

    /**
     *货物收货公司占比统计分析图
     * @param poundNum
     * @return
     */
    public JSONArray getReciveUnitAnalysis(String poundNum);
}
