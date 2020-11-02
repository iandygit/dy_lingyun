package com.lingyun.user.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lingyun.user.entity.MenuEntity;
import com.lingyun.user.service.MenuService;
import com.lingyun.user.service.RoleAuthService;
import com.lingyun.user.vo.MenuVo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("rolauth")
@Api(value = "权限管理",tags = {"权限管理"})
public class RoleAuthController {
    @Autowired
    private RoleAuthService roleAuthService;
    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "添加权限",tags = "权限管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId",value = "角色id"),
            @ApiImplicitParam(name = "menuIds",value = "菜单id，多个用英文逗号(,)拼接成字符串")
    })
    public ResponseEntity<String> addAuth(String roleId,String menuIds){
      if(StringUtils.isEmpty(roleId)){

          return ResponseEntity.ok("角色不能为空");
      }
      if(StringUtils.isEmpty(menuIds)){
          return ResponseEntity.ok("菜单不能为空");
      }
       String result= roleAuthService.savRoleAuth( roleId, menuIds);

       return ResponseEntity.ok(result);
    }




    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ApiOperation(value = "通过角色id获得权限列表",tags = "权限管理")
    public ResponseEntity<JSONArray> getAllAuth(Integer roleid){

        List<MenuEntity> menuEntities=menuService.findAllByRoleId(roleid);

        JSONArray jsonArray=new JSONArray();
        jsonArray.addAll(menuEntities);
        return  ResponseEntity.ok(jsonArray);
    }


    @RequestMapping(value = "/auth",method = RequestMethod.POST)
    @ApiOperation(value = "删除关联权限",tags = "权限管理")
    public ResponseEntity<JSONObject> deleteAuth(String roleid,String menuId){
        JSONObject object=new JSONObject();
        if(StringUtils.isEmpty(roleid) ||StringUtils.isEmpty(menuId)){
            object.put("status",201);
            object.put("mesg","操作失败，权限或者菜单不能为空");


        }
        object.put("status",200);
        object.put("mesg","操作成功");

        roleAuthService.deleteByRoleIdAndMenuId(Long.valueOf(roleid),Long.valueOf(menuId));



        return ResponseEntity.ok(object);

    }

}
