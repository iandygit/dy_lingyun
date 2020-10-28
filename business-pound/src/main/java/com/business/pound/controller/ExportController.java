package com.business.pound.controller;

import com.business.pound.entity.PoundEntity;
import com.business.pound.service.PoundService;

import com.business.pound.service.TransportService;
import com.business.pound.util.ExcelUtil;
import com.business.pound.vo.PoundTransVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/excel")
@Api(value = "导出excel",tags = "导出管理")
public class ExportController {
     Logger logger= LoggerFactory.getLogger(ExportController.class);
    @Autowired
    private PoundService poundService;
    @Autowired
    private TransportService transportService;

    @GetMapping("pound")
    @ApiOperation(value = "磅单记录导出",tags = "导出管理")
    public ResponseEntity<String>  exportPound(String poundAccount,  HttpServletResponse response){
        List<PoundEntity> poundEntities=null;
        if(StringUtils.isEmpty(poundAccount)){
             poundEntities= poundService.getAll();
        }else {

            poundEntities=poundService.findAllByPoundAccount(poundAccount);
        }



        String cloumns[]=new String []{"磅房号","磅单号","汽车号","货物名","收货单位","发货单位","毛重","皮重","净重","货物流向"};



        List list=new ArrayList();


        for(int i=0 ;i<poundEntities.size();i++){

            PoundEntity poundEntity=  poundEntities.get(i);
            String result[]=new String[cloumns.length];

            result[0]=poundEntity.getPoundAccount();//磅房号
            result[1]=poundEntity.getPoundNum();//磅房号
            result[2]=poundEntity.getCarNum();//汽车号
            result[3]=poundEntity.getGoodsName();//货物名
            result[4]=poundEntity.getReciveUnit();//收货单位
            result[5]=poundEntity.getDeliverUnit();//发货单位
            result[6]=String.valueOf(poundEntity.getWeight());//毛重
            result[7]=String.valueOf(poundEntity.getTareWeight());//皮重
            result[8]=String.valueOf(poundEntity.getNetWeight());//净重
            result[9]=poundEntity.getFlowTo().getDesc();//货物流向
            //result[10]=poundEntity.getPoundAccount();//磅房号
            list.add(result);
        }

        ExcelUtil excelUtil=new ExcelUtil("磅单导出记录",cloumns,list);
        ServletOutputStream outputStream=excelUtil.outStreamExcel(response);
        try {
            excelUtil.writeExcel(outputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    @GetMapping("transport")
    @ApiOperation(value = "运单记录导出",tags = "导出管理")
    public ResponseEntity<String>  exportTransport(String poundAccount,  HttpServletResponse response){

         List<PoundTransVo> transportEnetity= transportService.findAllList(poundAccount);
         String cloumns[]=new String []{"运单号","磅单号","汽车号","货物名","收货单位","发货单位","毛重","皮重","净重","货物流向","磅房号"};

        List list=new ArrayList();


        for(int i=0 ;i<transportEnetity.size();i++){

            PoundTransVo poundEntity=  transportEnetity.get(i);
            String result[]=new String[cloumns.length];
            result[0]=poundEntity.getTransportNum();//运单号
            result[1]=poundEntity.getPoundNum();//磅单号
            result[2]=poundEntity.getCarNum();//汽车号
            result[3]=poundEntity.getGoodsName();//货物名
            result[4]=poundEntity.getReciveUnit();//收货单位
            result[5]=poundEntity.getDeliverUnit();//发货单位
            result[6]=String.valueOf(poundEntity.getWeight());//毛重
            result[7]=String.valueOf(poundEntity.getTareWeight());//皮重
            result[8]=String.valueOf(poundEntity.getNetWeight());//净重
            result[9]=poundEntity.getFlowTo().getDesc();//货物流向
            result[10]=poundEntity.getPoundAccount();//磅房号
            list.add(result);
        }

        ExcelUtil excelUtil=new ExcelUtil("运单记录导出",cloumns,list);
        ServletOutputStream outputStream=excelUtil.outStreamExcel(response);
        try {
            excelUtil.writeExcel(outputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }


        return  null;
    }
}
