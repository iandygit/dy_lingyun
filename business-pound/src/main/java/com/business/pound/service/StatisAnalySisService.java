package com.business.pound.service;

import com.business.pound.entity.PoundEntity;

import java.util.List;
import java.util.Map;

public interface StatisAnalySisService {

    public List<Object[]> getWeightAnalySis(Integer day, String poundNum);
}
