package com.lingyun.user.controller;

import com.lingyun.core.beans.LoginUser;

import com.lingyun.user.entity.OrganizationEntity;
import com.lingyun.user.service.OrgSevice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("org")
@Api(value = "矿区管理",tags = {"矿区管理"})
public class OrgController {

    @Autowired
    private OrgSevice orgSevice;


    /**
     * 查询
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "矿区数据列表带分页", notes = "支持按照名称查询", tags = "矿区管理")
    public ResponseEntity<Page<OrganizationEntity>> pageOrg( String  orgName, Integer pageNum,Integer pageSize) {
        if(null== pageNum || pageNum==0){
            pageNum=0;
        }else {
            pageNum=pageNum-1;
        }
        if(null==pageSize){
            pageSize=20;
        }
        Pageable pageable= new PageRequest(pageNum,pageSize);

        Page<OrganizationEntity> list = this.orgSevice.findAllByOrgName(orgName,pageable);


        return ResponseEntity.ok(list);
    }

    /**
     * 显示所有的矿区数据
     * @return
     */
    @RequestMapping( method = RequestMethod.GET)
    @ApiOperation(value = "矿区数据列表", notes = "支持按照名称查询", tags = "矿区管理")
    public ResponseEntity<List<OrganizationEntity>> list() {


        List<OrganizationEntity> list = this.orgSevice.getAll();


        return ResponseEntity.ok(list);
    }

    /**
     * 通过id获取对象
     * @return
     */
    @RequestMapping(value = "/one/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "获取单个矿区对象",  tags = "矿区管理")
    public ResponseEntity<OrganizationEntity> getOne(@PathVariable("id")String id){

        if(StringUtils.isEmpty(id)){
            return null;
        }


        return ResponseEntity.ok(orgSevice.getOne(Long.valueOf(id)));
    }

    /**
     * 添加
     * @return
     */
    @RequestMapping(value = "/{orgName}", method = RequestMethod.POST)
    @ApiOperation(value = "添加/编辑矿区", notes = "添加/编辑矿区", tags = "矿区管理")
    public  ResponseEntity<String> savee(OrganizationEntity organizationEntity){
        String org=orgSevice.saveOrg(organizationEntity);

            return ResponseEntity.ok(org);
    }


}
