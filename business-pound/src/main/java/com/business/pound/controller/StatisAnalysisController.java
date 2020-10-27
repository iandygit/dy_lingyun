package com.business.pound.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.List;
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
            @ApiImplicitParam(name = "poundAccount",value = "磅房号",dataType = "string")
    })
    public ResponseEntity<JSONObject>  geteWeightAnalysis(Integer day, String poundAccount){
        JSONObject jsonObject=new JSONObject();
        if(day==null){
            day=7;
        }
        List<Object[]> mapList=statisAnalySisService.getWeightAnalySis(day,poundAccount);
        if(null==mapList ||mapList.size()==0){

            return ResponseEntity.ok(null);
        }
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
    @RequestMapping(value = "delivUnit",method = RequestMethod.GET)
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 401, message = "权限不足") })
    @ApiOperation(value = "货物发货公司占比统计分析图",tags = "统计分析管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "poundAccount",value = "磅房号",dataType = "string")
    })
    public ResponseEntity<JSONArray> getDelivUnitAnalysis(String poundAccount){

        JSONArray jsonArray= statisAnalySisService.getDeliverUnitAnalysis(poundAccount);


        return  ResponseEntity.ok(jsonArray);
    }

    @RequestMapping(value = "recUnit",method = RequestMethod.GET)
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 401, message = "权限不足") })
    @ApiOperation(value = "货物收货公司占比统计分析图",tags = "统计分析管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "poundAccount",value = "磅房号",dataType = "string")
    })
    public ResponseEntity<JSONArray> getRecUnitAnalysis(String poundAccount){

        JSONArray jsonArray=statisAnalySisService.getReciveUnitAnalysis(poundAccount);

        return  ResponseEntity.ok(jsonArray);
    }

}
