package com.business.pound.controller;

import com.business.pound.entity.PoundEntity;
import com.business.pound.service.PoundService;
import com.business.pound.util.PoundEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private PoundService poundService;


    /**
     * 磅单记录查询
     * @return
     */
    @RequestMapping( method = RequestMethod.GET)
    @ApiOperation(value = "磅单数据列表", notes = "支持按照名称查询", tags = "磅单管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "poundAccount",value = "磅房号"),
            @ApiImplicitParam(name = "pageNum",value = "当前页数，不传递默认是1"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示大小，不传递默认是20")
    })
    public ResponseEntity<Page<PoundEntity>> all(String poundAccount,Integer pageNum,Integer pageSize){
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

        //matcher.withMatcher("poundStatus",ExampleMatcher.GenericPropertyMatchers.contains());
        //entityExample.setPoundStatus(PoundEnum.APPORVAL_O);//未审批状态的数据

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
