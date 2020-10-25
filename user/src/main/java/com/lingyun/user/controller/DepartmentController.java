package com.lingyun.user.controller;

import com.lingyun.user.entity.DepartmentEntity;
import com.lingyun.user.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("department")
@Api(value = "部门管理",tags = {"部门管理"})
public class DepartmentController {
    Logger logger= LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    private DepartmentService departmentService;


    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "部门数据列表", notes = "部门管理", tags = "部门管理")
    public  ResponseEntity<List<DepartmentEntity>> findAll(){

        List<DepartmentEntity> list= departmentService.findAll();

        return ResponseEntity.ok(list);

    }

    @RequestMapping(value = "/{depName}",method = RequestMethod.GET)
    @ApiOperation(value = "部门数据列表分页", notes = "部门管理", tags = "部门管理")
    public  ResponseEntity<Page<DepartmentEntity>> findAllPage(@PathVariable("depName")String depName,Integer pageNum,Integer pageSize){

        Pageable pageable=new PageRequest(pageNum,pageSize);
        Page<DepartmentEntity> list= departmentService.findPageByDepName(depName,pageable);

        return ResponseEntity.ok(list);

    }
    @RequestMapping(value = "/one/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "部门数据列表分页", notes = "部门管理", tags = "部门管理")
    public ResponseEntity<DepartmentEntity> getOne(@PathVariable("id")String id){

        return ResponseEntity.ok(departmentService.getOne(Long.valueOf(id)));

    }

    @RequestMapping(value = "/{depName}",method = RequestMethod.POST)
    @ApiOperation(value = "添加/编辑部门", notes = "部门管理", tags = "部门管理")
    public ResponseEntity<String> save(@PathVariable("depName")String depName,DepartmentEntity departmentEntity){

        departmentService.save(departmentEntity);
        logger.info("保存成功");
        return ResponseEntity.ok("操作成功");
    }
}