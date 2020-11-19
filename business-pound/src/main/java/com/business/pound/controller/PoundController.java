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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
            @ApiImplicitParam(name = "startTime",value = "查询开始日期"),
            @ApiImplicitParam(name = "endTime",value = "结束开始日期"),
            @ApiImplicitParam(name = "pageNum",value = "当前页数，不传递默认是1"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示大小，不传递默认是20")
    })
    public ResponseEntity<Page<PoundEntity>> all(String poundAccount,String startTime,String endTime,Integer pageNum,Integer pageSize){
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

        Specification specification = new Specification() {

            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                //增加筛选条件
                Predicate predicate = cb.conjunction();
                if(StringUtils.isNotBlank(poundAccount)){
                    predicate.getExpressions().add(cb.equal(root.get("poundAccount"), poundAccount));
                }

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {

                 //起始日期
                if (startTime != null && !startTime.trim().equals("")) {

                    predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createTime").as(String.class),startTime ));
                }
                  //结束日期
                if (endTime != null && !endTime.trim().equals("")) {
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(format.parse(endTime));
                    calendar.add(calendar.DATE,1); //把日期往后增加一天,整数  往后推,负数往前移动
                    predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createTime").as(String.class),format.format(calendar.getTime())));
                }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return predicate;
            }
        };


        Page<PoundEntity> list= poundService.findAll(specification, pageable);

        return ResponseEntity.ok(list);
    }

    /**
     *
     * @return
     */
    @RequestMapping( method = RequestMethod.POST)
    @ApiOperation(value = "磅单/编辑磅单记录", notes = "插入/编辑操作", tags = "磅单管理")
    public ResponseEntity<String> save(PoundEntity poundEntity){

         poundService.save(poundEntity);

        return ResponseEntity.ok("操作成功");
    }
    @RequestMapping( method = RequestMethod.DELETE)
    @ApiOperation(value = "删除磅单信息", notes = "删除操作操作", tags = "磅单管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "poundAccount",value = "磅房号"),
            @ApiImplicitParam(name = "poundNum",value = "磅单号"),
            @ApiImplicitParam(name = "poundId",value = "磅单id")
    })
    public  ResponseEntity<String> deletePound(Long poundId,String poundNum,String poundAccount){

       return poundService.deleteByIdNumAccount(poundId,poundNum,poundAccount);
    }
}
