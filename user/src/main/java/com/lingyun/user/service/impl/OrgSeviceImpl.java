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
import java.util.Optional;

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
    public String saveOrg(OrganizationEntity organizationEntity) {
        if(null== organizationEntity.getId() ||organizationEntity.getId()==0){//插入操作
            OrganizationEntity resultEntity=orgRepository.findByOrgName(organizationEntity.getOrgName());
            if(null==resultEntity){//名称不存在可以插入

                OrganizationEntity org= orgRepository.save(organizationEntity);
                if(null ==org){
                    return "操作失败";
                }
                 return "操作成功";
            }
        }else {//更新操作

            OrganizationEntity optional=orgRepository.findByIdAndOrgName(organizationEntity.getId(),organizationEntity.getOrgName());
            if(null==optional){
                return "实体不存在，请检查数据是否合法";
            }
            //名称不允许重复
            organizationEntity.setOrgName(optional.getOrgName());//名称不允许修改
            OrganizationEntity res=orgRepository.saveAndFlush(organizationEntity);
            if(null!=res){
                return "操作成功";
            }
            return "操作失败";
        }
        return "操作成功";
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
        Optional<OrganizationEntity> organizationEntityOptional=orgRepository.findById(id);
        if(null==organizationEntityOptional){
            return null;
        }
        return organizationEntityOptional.get();
    }

    @Override
    public void deleteById(Long id) {
        orgRepository.deleteById(id);
    }
}
