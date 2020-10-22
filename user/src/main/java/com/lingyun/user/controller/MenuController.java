package com.lingyun.user.controller;

import com.lingyun.user.entity.MenuEntity;
import com.lingyun.user.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("menu")
@Api(value = "菜单管理",tags = {"菜单管理"},produces ="1")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "获取菜单下拉数据",tags = "菜单管理")
    public ResponseEntity<List<MenuEntity>> all(){
     List<MenuEntity> list=menuService.findAll();
     if(null==list){
         return ResponseEntity.status(201).body(null);
     }
     return ResponseEntity.ok(list);

    }
    @RequestMapping(value = "/",method = RequestMethod.POST)
    @ApiOperation(value = "添加/编辑菜单",tags = "菜单管理")
    public ResponseEntity<String> saveMenu(MenuEntity menuEntity){
        MenuEntity entity=menuService.save(menuEntity);
        if(null==entity || null==entity.getId()){
            return ResponseEntity.status(201).body("添加失败");
        }
        return ResponseEntity.ok("添加成功");

    }

}
