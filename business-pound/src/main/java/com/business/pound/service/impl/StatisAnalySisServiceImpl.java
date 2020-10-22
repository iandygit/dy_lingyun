package com.business.pound.service.impl;

import com.business.pound.entity.PoundEntity;
import com.business.pound.repository.PoundRepository;
import com.business.pound.service.StatisAnalySisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StatisAnalySisServiceImpl implements StatisAnalySisService {
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
}
