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

    //运单列表数据分页
    @Query(value = "select new  com.business.pound.vo.PoundTransVo(t.id,p.id,t.transportNum,p.poundNum,p.carNum,p.goodsName,p.reciveUnit,p.deliverUnit,p.weight," +
            "p.tareWeight,p.netWeight,p.poundWeight,p.flowTo,p.poundAccount) from PoundEntity p left join  TransportEnetity t on p.transportNum=t.transportNum where (p.transportNum=:transportNum and p.transportNum is not null)")
    public Page<PoundTransVo>  findAllTransport(@Param("transportNum") String transportNum, Pageable pageable);

    //运单列表数据分页
    @Query(value = "select new  com.business.pound.vo.PoundTransVo(t.id,p.id,t.transportNum,p.poundNum,p.carNum,p.goodsName,p.reciveUnit,p.deliverUnit,p.weight," +
            "p.tareWeight,p.netWeight,p.poundWeight,p.flowTo,p.poundAccount) from PoundEntity p left join  TransportEnetity t on p.transportNum=t.transportNum where p.transportNum is not null ")
    public Page<PoundTransVo>  findAllTransport(Pageable pageable);

    //运单记录导出
    @Query(value = "select new  com.business.pound.vo.PoundTransVo(t.id,p.id,t.transportNum,p.poundNum,p.carNum,p.goodsName,p.reciveUnit,p.deliverUnit,p.weight," +
            "p.tareWeight,p.netWeight,p.poundWeight,p.flowTo,t.poundAccount) from PoundEntity p left join  TransportEnetity t on p.transportNum=t.transportNum where (p.transportNum=:transportNum  and p.transportNum is not null)")
    public List<PoundTransVo> findAllList(@Param("transportNum")String transportNum );
    //运单记录导出
    @Query(value = "select new  com.business.pound.vo.PoundTransVo(t.id,p.id,t.transportNum,p.poundNum,p.carNum,p.goodsName,p.reciveUnit,p.deliverUnit,p.weight," +
            "p.tareWeight,p.netWeight,p.poundWeight,p.flowTo,t.poundAccount) from PoundEntity p left join  TransportEnetity t on p.transportNum=t.transportNum where p.transportNum is not null ")
    public List<PoundTransVo> findAllList();


    public Page<TransportEnetity> findAllByTransportNum(String transportNum,Pageable pageable);
}
