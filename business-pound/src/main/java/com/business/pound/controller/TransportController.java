package com.business.pound.controller;


import com.business.pound.service.TransportService;
import com.business.pound.vo.PoundTransVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/trasport")
@RestController
@Api(value = "运单管理",tags = "运单管理")
public class TransportController {
   Logger logger= LoggerFactory.getLogger(TransportController.class);
   @Autowired
   private TransportService transportService;


    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ApiOperation(value = "运单列表数据分页",tags = "运单管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "transportNum",value = "运单号号",dataType = "java.lang.String"),
            @ApiImplicitParam(name = "pageNum",value = "当前页数，不传递默认是1"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示大小，不传递默认是20")
    })
    public ResponseEntity<Page<PoundTransVo>> all(String transportNum, Integer pageNumber, Integer pageSize){
         if(null==pageNumber ||pageNumber==0){
             pageNumber=0;
         }else {
             pageNumber=pageNumber-1;
         }
         if(null==pageSize){
             pageSize=20;
         }
        //Sort sort = new Sort(Sort.Direction.DESC,"createTime"); //创建时间降序排序
        Pageable pageable = new PageRequest(pageNumber,pageSize);


        Page<PoundTransVo> poundTransVos=transportService.getPageTransport(transportNum,pageable);
        return ResponseEntity.ok(poundTransVos);
    }
}