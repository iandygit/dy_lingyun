package com.business.pound.controller;

import com.business.pound.entity.PoundEntity;
import com.business.pound.service.PoundService;
import com.business.pound.util.PoundEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pound")
@Api(value = "磅单管理",tags = {"磅单管理"})
public class PoundController {
    Logger logger= LoggerFactory.getLogger(PoundController.class);
    @Autowired
    private PoundService poundService;


    /**
     * 磅单记录查询
     * @return
     */
    @RequestMapping( method = RequestMethod.GET)
    @ApiOperation(value = "磅单数据列表", notes = "支持按照名称查询，状态查询", tags = "磅单管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "poundAccount",value = "磅房号"),
            @ApiImplicitParam(name = "poundStatus",value = "磅单状态",dataType = "String", paramType = "query",
                    allowableValues = "APPORVAL_W,APPORVAL_A,APPORVAL_B", allowMultiple = false),
            @ApiImplicitParam(name = "pageNum",value = "当前页数，不传递默认是1"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示大小，不传递默认是20")
    })
    public ResponseEntity<Page<PoundEntity>> all(String poundAccount,String poundStatus,Integer pageNum,Integer pageSize){
        if(null==pageNum || pageNum==0){
            pageNum=0;
        }else {
            pageNum=pageNum-1;
        }
        if(null==pageSize){
            pageSize=20;
        }
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable= new PageRequest(pageNum,pageSize,sort);
        PoundEntity entityExample=new PoundEntity();
        ExampleMatcher matcher = ExampleMatcher.matching();



        matcher.withMatcher("poundStatus",ExampleMatcher.GenericPropertyMatchers.contains());
        if(StringUtils.isNoneEmpty(poundStatus)){
            String poundStatusCode=PoundEnum.valueOf(poundStatus).getCode();
            logger.info("状态参数："+poundStatusCode);
            entityExample.setPoundStatus(PoundEnum.valueOf(poundStatus));//未审批状态的数据

        }else {
            entityExample.setPoundStatus(PoundEnum.APPORVAL_W);//未审批状态的数据

        }

        if(StringUtils.isNoneEmpty(poundAccount)){
            matcher.withMatcher("poundAccount",ExampleMatcher.GenericPropertyMatchers.contains());
            entityExample.setPoundAccount(poundAccount);
        }

        Example<PoundEntity> example = Example.of(entityExample ,matcher);
        Page<PoundEntity> list= poundService.getPage(example,pageable);

        return ResponseEntity.ok(list);
    }

    /**
     *
     * @return
     */
    @RequestMapping( method = RequestMethod.POST)
    @ApiOperation(value = "磅单/编辑磅单记录", notes = "插入/编辑操作", tags = "磅单管理")
    public ResponseEntity<String> save(@RequestBody  PoundEntity poundEntity){

         poundService.save(poundEntity);

        return ResponseEntity.ok("操作成功");
    }
}
