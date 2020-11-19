package com.business.pound.service;


import com.business.pound.entity.PoundEntity;
import com.business.pound.util.PoundEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import java.util.List;
public interface PoundService extends  BaseService<PoundEntity> {


    public  List<PoundEntity> findAllByPoundNum(String poundNum);

    public List<PoundEntity>  findAllByPoundAccount(String poundAccount);

    /**
     * 删除磅单信息
     * @param id
     * @param num
     * @param account
     * @return
     */
    public ResponseEntity<String> deleteByIdNumAccount(Long id,String num,String account);

    public List<PoundEntity> findAllByIsEnabled(PoundEnum isEnable);

    /**
     * 磅单导出升级，增加时间查询
     * @param poundAccount
     * @param isEnable
     * @param startTime
     * @param endTime
     * @return
     */
    public List<PoundEntity>  getExportResult( Specification specification );

    /**
     * pound动态查询
     * @param specification
     * @param Pageable
     * @return
     */
    public Page<PoundEntity> findAll(Specification specification , Pageable pageRequest);
}
