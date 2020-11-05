package com.lingyun.user.dao;

import com.lingyun.user.entity.OrganizationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgRepository extends JpaRepository<OrganizationEntity,Long> {

    public Page<OrganizationEntity>  findAllByOrgName(String orgName, Pageable pageable);

    public OrganizationEntity findByOrgName(String orgName);


    public  OrganizationEntity findByIdAndOrgName(Long id,String orgName);
}
