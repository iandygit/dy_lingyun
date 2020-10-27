package com.business.pound.repository;

import com.business.pound.entity.TransportEnetity;
import com.business.pound.vo.PoundTransVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransportRepository  extends JpaRepository<TransportEnetity,Long> {


    @Query(value = "select new  com.business.pound.vo.PoundTransVo(t.id,t.poundId,t.transportNum,t.poundNum,p.carNum,p.goodsName,p.reciveUnit,p.deliverUnit,p.weight," +
            "p.tareWeight,p.netWeight,p.poundWeight,p.flowTo,p.poundAccount) from TransportEnetity t left join PoundEntity p on t.poundId=p.id where t.transportNum=:transportNum")
    public Page<PoundTransVo>  findAllTransport(@Param("transportNum") String transportNum, Pageable pageable);

    @Query(value = "select new  com.business.pound.vo.PoundTransVo(t.id,t.poundId,t.transportNum,t.poundNum,p.carNum,p.goodsName,p.reciveUnit,p.deliverUnit,p.weight," +
            "p.tareWeight,p.netWeight,p.poundWeight,p.flowTo,p.poundAccount) from TransportEnetity t left join PoundEntity p on t.poundId=p.id ")
    public Page<PoundTransVo>  findAllTransport(Pageable pageable);

    @Query(value = "select new  com.business.pound.vo.PoundTransVo(t.id,t.poundId,t.transportNum,t.poundNum,p.carNum,p.goodsName,p.reciveUnit,p.deliverUnit,p.weight," +
            "p.tareWeight,p.netWeight,p.poundWeight,p.flowTo,p.poundAccount) from TransportEnetity t left join PoundEntity p on t.poundId=p.id")
    public List<PoundTransVo> findAllList();



}
