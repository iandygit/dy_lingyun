package com.lingyun.user.service;

import com.lingyun.user.entity.OrganizationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrgSevice extends BaseService<OrganizationEntity>{
    //列表分页
    public Page<OrganizationEntity> findAllByOrgName(String orgName, Pageable pageable);


}
