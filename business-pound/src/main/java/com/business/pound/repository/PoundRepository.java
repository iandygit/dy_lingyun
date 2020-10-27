package com.business.pound.repository;

import com.business.pound.entity.PoundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface PoundRepository extends JpaRepository<PoundEntity,Long> {


    @Query(value = "select sum(p.actual_weight) weight,p.pound_account from t_pound p   where DATE_FORMAT(p.create_time,'%Y-%m-%d') >= DATE_SUB( CURDATE( ), INTERVAL 7 day ) and (:poundNum is null or p.pound_account=:poundNum) group by p.pound_account",nativeQuery = true)
    public List<Object[]> findAllByPoundNumDay7( @Param("poundNum")String poundNum);

    @Query(value = "select sum(p.actual_weight) weight,p.pound_account from t_pound p   where DATE_FORMAT(p.create_time,'%Y-%m-%d') >= DATE_SUB( CURDATE( ), INTERVAL 30  day ) and (:poundNum is null or p.pound_account=:poundNum) group by p.pound_account",nativeQuery = true)
    public List<Object[]> findAllByPoundNumDay30( @Param("poundNum")String poundNum);
}
