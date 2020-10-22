package com.lingyun.user.controller;


import com.lingyun.user.service.RoleAuthService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rolauth")
@Api(value = "权限管理",tags = {"权限管理"})
public class RoleAuthController {
    @Autowired
    private RoleAuthService roleAuthService;

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


}
