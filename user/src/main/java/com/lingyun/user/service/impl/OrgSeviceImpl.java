package com.lingyun.user.service.impl;

import com.lingyun.user.dao.OrgRepository;
import com.lingyun.user.entity.OrganizationEntity;
import com.lingyun.user.service.OrgSevice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgSeviceImpl implements OrgSevice {
    @Autowired
    private OrgRepository orgRepository;
    @Override
    public Page<OrganizationEntity> findAllByOrgName(String orgName, Pageable pageable) {
        OrganizationEntity entityExample=new OrganizationEntity();
        ExampleMatcher matcher = ExampleMatcher.matching();
        if(StringUtils.isNoneEmpty(orgName)){
            matcher.withMatcher("orgName",ExampleMatcher.GenericPropertyMatchers.startsWith());
            entityExample.setOrgName(orgName);
        }

        Example<OrganizationEntity> example = Example.of(entityExample ,matcher);
        return orgRepository.findAll(example,pageable);
        /* if(StringUtils.isEmpty(orgName)){

             Page<OrganizationEntity> organizationEntities=orgRepository.findAll(pageable);
             return organizationEntities;

         }

        return orgRepository.findAllByOrgName(orgName,pageable);*/
    }

    @Override
    public OrganizationEntity save(OrganizationEntity organizationEntity) {

        return orgRepository.saveAndFlush(organizationEntity);
    }

    @Override
    public void delete(OrganizationEntity organizationEntity) {
        orgRepository.delete(organizationEntity);
    }

    @Override
    public List<OrganizationEntity> getAll() {
        return orgRepository.findAll();
    }

    @Override
    public Page<OrganizationEntity> getPage(Example<OrganizationEntity> example, Pageable pageable) {

        return null;
    }

    @Override
    public OrganizationEntity getOne(Long id) {

        return orgRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        orgRepository.deleteById(id);
    }
}
