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
    public List<Object[]> getWeightAnalySis(Integer day, String poundNum) {
        List<Object[]> mapList=new ArrayList<Object[]>();
        if(day==7){
            mapList=poundRepository.findAllByPoundNumDay7(poundNum);
        }else if(day==30){
            mapList=poundRepository.findAllByPoundNumDay30(poundNum);
        }else {
            return null;
        }

        return mapList;
    }

    @Override
    public JSONArray getDeliverUnitAnalysis(String poundNum) {

         List<StatisAnalySIsB> objects=poundRepository.findAllByPoundNumDeDeUnit(poundNum);


          JSONArray jsonArray=new JSONArray();
          jsonArray.addAll(objects);

        return jsonArray;
    }

    @Override
    public JSONArray getReciveUnitAnalysis(String poundNum) {

        List<StatisAnalySIsB> objects=poundRepository.findAllByPoundNumRecUnit(poundNum);
        JSONArray jsonArray=new JSONArray();
        jsonArray.addAll(objects);



        return jsonArray;
    }
}
