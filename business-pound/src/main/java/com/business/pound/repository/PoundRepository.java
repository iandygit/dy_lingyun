package com.business.pound.repository;

import com.business.pound.entity.PoundEntity;
import com.business.pound.vo.StatisAnalySIsB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PoundRepository extends JpaRepository<PoundEntity,Long> {


    @Query(value = "select sum(p.actual_weight) weight,p.pound_account from t_pound p   where DATE_FORMAT(p.create_time,'%Y-%m-%d') >= DATE_SUB( CURDATE( ), INTERVAL 7 day ) and (:poundNum is null or p.pound_account=:poundNum) group by p.pound_account",nativeQuery = true)
    public List<Object[]> findAllByPoundNumDay7( @Param("poundNum")String poundNum);

    @Query(value = "select   sum(p.actual_weight) weight,p.pound_account from t_pound p   where DATE_FORMAT(p.create_time,'%Y-%m-%d') >= DATE_SUB( CURDATE( ), INTERVAL 30  day ) and (:poundNum is null or p.pound_account=:poundNum) group by p.pound_account",nativeQuery = true)
    public List<Object[]> findAllByPoundNumDay30( @Param("poundNum")String poundNum);

    //@Query(value = "select   new  com.business.pound.vo.StatisAnalySIsB(p.deliverUnit,count(p.deliverUnit)/(select count(1)" +
    //        " from PoundEntity where deliverUnit is not null and (:poundNum is null or p.poundNum=:poundNum))) " +
    //        "from PoundEntity p where  (:poundNum is null or p.poundNum=:poundNum) group by p.deliverUnit")
    //public List<StatisAnalySIsB> findAllByPoundNumDeDeUnit(@Param("poundNum")String poundNum);

    @Query(value = "select   new  com.business.pound.vo.StatisAnalySIsB(p.deliverUnit,count(p.deliverUnit),(select count(1)  " +
            " from PoundEntity where deliverUnit is not null and (:poundNum is null or poundNum=:poundNum)) as vo) " +
            "from PoundEntity p where  (:poundNum is null or p.poundNum=:poundNum) group by p.deliverUnit")
    public List<StatisAnalySIsB> findAllByPoundNumDeDeUnit(@Param("poundNum")String poundNum);

    @Query(value = "select   new  com.business.pound.vo.StatisAnalySIsB(p.reciveUnit,count(p.reciveUnit),(select count(1) as total " +
            "from PoundEntity where reciveUnit is not null and  (:poundNum is null or poundNum=:poundNum)) )" +
            " from PoundEntity p where  (:poundNum is null or p.poundNum=:poundNum) group by p.reciveUnit")
    public List<StatisAnalySIsB> findAllByPoundNumRecUnit(@Param("poundNum")String poundNum);


    public List<PoundEntity> findAllByPoundNum(String poundName);
}
