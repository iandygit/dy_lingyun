package com.business.pound.controller;


import com.alibaba.fastjson.JSONObject;
import com.business.pound.entity.PoundEntity;
import com.business.pound.service.PoundService;
import com.business.pound.service.StatisAnalySisService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/analysis")
@Api(value = "统计分析",tags = {"统计分析管理"})
public class StatisAnalysisController {
     Logger logger= LoggerFactory.getLogger(StatisAnalysisController.class);
    @Autowired
    private StatisAnalySisService statisAnalySisService;



    @RequestMapping(value = "weight",method = RequestMethod.GET)
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 401, message = "权限不足") })
    @ApiOperation(value = "货物流动总重量",tags = "统计分析管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "day",value = "统计天数",dataType = "int"),
            @ApiImplicitParam(name = "poundNum",value = "磅房号",dataType = "string")
    })
    public ResponseEntity<JSONObject>  geteWeightAnalysis(Integer day, String poundNum){
        JSONObject jsonObject=new JSONObject();
        List<Object[]> mapList=statisAnalySisService.getWeightAnalySis(day,poundNum);

        List<Double> values=new ArrayList<>();
        List<String> colums=new ArrayList<>();
        for (Object[] objects:mapList){

            values.add(Double.valueOf(objects[0].toString()));
            colums.add(objects[1].toString());
        }
        jsonObject.put("categoriees",colums);
        jsonObject.put("data",values);
        return ResponseEntity.ok(jsonObject);
    }
}
