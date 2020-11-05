package com.business.pound.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.business.pound.repository.PoundRepository;
import com.business.pound.service.StatisAnalySisService;
import com.business.pound.vo.StatisAnalySIsB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisAnalySisServiceImpl implements StatisAnalySisService {

    Logger logger= LoggerFactory.getLogger(StatisAnalySisServiceImpl.class);
    @Autowired
    private PoundRepository poundRepository;

    @Override
    public List<Object[]> getWeightAnalySis(Integer day, String poundAccount) {
        List<Object[]> mapList=new ArrayList<Object[]>();
        if(day==7){
            mapList=poundRepository.findAllByPoundNumDay7(poundAccount);
        }else if(day==30){
            mapList=poundRepository.findAllByPoundNumDay30(poundAccount);
        }else {
            return null;
        }

        return mapList;
    }

    @Override
    public JSONArray getDeliverUnitAnalysis(String poundAccount) {

         List<StatisAnalySIsB> objects=poundRepository.findAllByPoundAccDeDeUnit(poundAccount);


          JSONArray jsonArray=new JSONArray();
          jsonArray.addAll(objects);

        return jsonArray;
    }

    @Override
    public JSONArray getReciveUnitAnalysis(String poundAccount) {

        List<StatisAnalySIsB> objects=poundRepository.findAllByPoundAccRecUnit(poundAccount);
        JSONArray jsonArray=new JSONArray();
        jsonArray.addAll(objects);



        return jsonArray;
    }
}
