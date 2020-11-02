package com.lingyun.user.controller;

import com.lingyun.user.entity.RoleEntity;
import com.lingyun.user.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("role")
@Api(value = "角色管理",tags = {"角色管理"})
public class RoleController {

    @Autowired
    private RoleService roleService;


    @RequestMapping( method = RequestMethod.GET)
    @ApiOperation(value = "角色列表", notes = "获取所有角色，不分页", tags = "角色管理")
    public ResponseEntity<List<RoleEntity>> all(){

        List<RoleEntity> list=roleService.findAll();
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/one/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "获取单个角色",notes = "通过id获取",tags = "角色管理")
    public ResponseEntity<RoleEntity> getOne(@PathVariable("id") String id){

        return ResponseEntity.ok(roleService.getOne(Long.valueOf(id)));
    }

    @RequestMapping( method = RequestMethod.POST)
    @ApiOperation(value = "添加/编辑角色", notes = "只插入角色表", tags = "角色管理")
    public ResponseEntity<String> addRole(RoleEntity roleEntity){

        RoleEntity role=roleService.save(roleEntity);

        return ResponseEntity.ok("操作成功");
    }

}
