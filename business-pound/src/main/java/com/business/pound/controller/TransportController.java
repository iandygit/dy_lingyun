package com.business.pound.controller;


import com.business.pound.entity.TransportEnetity;
import com.business.pound.service.TransportService;
import com.business.pound.vo.PoundTransVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
            @ApiImplicitParam(name = "pageNumber",value = "当前页数，不传递默认是1"),
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
        Pageable pageable = new PageRequest(pageNumber,pageSize, Sort.by("id").descending());


        Page<PoundTransVo> poundTransVos=transportService.getPageTransport(transportNum,pageable);
        return ResponseEntity.ok(poundTransVos);
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    @ApiOperation(value = "运单编辑",tags = "运单管理")
    public ResponseEntity<String> updateTrans(TransportEnetity enetity ){
         if(null==enetity || null == enetity.getId()){

             return ResponseEntity.ok("false");
         }

        transportService.save(enetity);
        return ResponseEntity.ok("true");
    }
    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取实体",tags = "运单管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "运单id",dataType = "string")
    })
    public ResponseEntity<TransportEnetity>   getOneTran(@PathVariable("id") String id){
         if(StringUtils.isEmpty(id)){
             return null;
         }
        TransportEnetity enetity=transportService.getOne(Long.valueOf(id));

         return ResponseEntity.ok(enetity);
    }

    @RequestMapping(value = "apporval",method = RequestMethod.GET)
    @ApiOperation(value = "磅单审批操作",tags = "运单管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "磅单id，用英文的逗号拼接起来",dataType = "com.business.pound.util.PoundEnum")
    })
    public  ResponseEntity<String>  apporvalName(String ids,String status){

         if(!StringUtils.isEmpty(ids)){
           String id[]=ids.split(",");

             transportService.apporval(id,status);


             return  ResponseEntity.ok("操作成功");
         }


        return  ResponseEntity.ok("操作失败");
    }
}
