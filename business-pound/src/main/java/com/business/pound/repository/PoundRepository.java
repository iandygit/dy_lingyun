package com.business.pound.repository;

import com.business.pound.entity.PoundEntity;
import com.business.pound.util.PoundEnum;
import com.business.pound.vo.StatisAnalySIsB;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PoundRepository extends JpaRepository<PoundEntity,Long> {


    @Query(value = "select sum(p.actual_weight) weight,p.pound_account from t_pound p   where DATE_FORMAT(p.create_time,'%Y-%m-%d') >= DATE_SUB( CURDATE( ), INTERVAL 7 day ) and is_enabled='Y' and (:poundAccount is null or p.pound_account=:poundAccount) group by p.pound_account",nativeQuery = true)
    public List<Object[]> findAllByPoundNumDay7( @Param("poundAccount")String poundAccount);

    @Query(value = "select   sum(p.actual_weight) weight,p.pound_account from t_pound p   where DATE_FORMAT(p.create_time,'%Y-%m-%d') >= DATE_SUB( CURDATE( ), INTERVAL 30  day ) and is_enabled='Y' and (:poundAccount is null or p.pound_account=:poundAccount) group by p.pound_account",nativeQuery = true)
    public List<Object[]> findAllByPoundNumDay30( @Param("poundAccount")String poundAccount);



    @Query(value = "select   new  com.business.pound.vo.StatisAnalySIsB(p.deliverUnit,count(p.deliverUnit),(select count(1)  " +
            " from PoundEntity where deliverUnit is not null and (:poundNum is null or poundNum=:poundNum)) as vo) " +
            "from PoundEntity p where  (:poundNum is null or p.poundNum=:poundNum) group by p.deliverUnit")
    public List<StatisAnalySIsB> findAllByPoundNumDeDeUnit(@Param("poundNum")String poundNum);


    /**
     * 通过磅房号获取发货公司占比统计结果
     * @param poundAccount
     * @return
     */
    @Query(value = "select   new  com.business.pound.vo.StatisAnalySIsB(p.deliverUnit,count(p.deliverUnit),(select count(1)  " +
            " from PoundEntity where deliverUnit is not null and isEnabled='Y' and (:poundAccount is null or :poundAccount='' or poundAccount=:poundAccount)) as vo) " +
            "from PoundEntity p where   isEnabled='Y' and  (:poundAccount is null or :poundAccount='' or p.poundAccount=:poundAccount) group by p.deliverUnit")
    public List<StatisAnalySIsB> findAllByPoundAccDeDeUnit(@Param("poundAccount")String poundAccount);

    /**
     * 通过磅单号获取收货公司占比统计结果
     * @param poundNum
     * @return
     */
    @Query(value = "select   new  com.business.pound.vo.StatisAnalySIsB(p.reciveUnit,count(p.reciveUnit),(select count(1) as total " +
            "from PoundEntity where   isEnabled='Y' and reciveUnit is not null and  (:poundNum is null or :poundAccount='' or poundNum=:poundNum)) )" +
            " from PoundEntity p where   isEnabled='Y' and (:poundNum is null or p.poundNum=:poundNum) group by p.reciveUnit")
    public List<StatisAnalySIsB> findAllByPoundNumRecUnit(@Param("poundNum")String poundNum);

    /**
     * 通过磅房号获收货公司占比统计结果
     * @param poundAccount
     * @return
     */
    @Query(value = "select   new  com.business.pound.vo.StatisAnalySIsB(p.reciveUnit,count(p.reciveUnit),(select count(1) as total " +
            "from PoundEntity where   isEnabled='Y' and reciveUnit is not null and  (:poundAccount is null or :poundAccount='' or poundAccount=:poundAccount)) )" +
            " from PoundEntity p where    isEnabled='Y' and (:poundAccount is null or :poundAccount='' or p.poundAccount=:poundAccount) group by p.reciveUnit")
    public List<StatisAnalySIsB> findAllByPoundAccRecUnit(@Param("poundAccount")String poundAccount);

    /**
     * 通过磅单号查询实体
     * @param poundName
     * @return
     */
    public List<PoundEntity> findAllByPoundNum(String poundName);

    /***
     * 通过磅房号查询实体
     * @param poundAccount
     * @return
     */
    public List<PoundEntity> findAllByPoundAccountAndIsEnabled(String poundAccount, PoundEnum isEnable);


    public List<PoundEntity> findAllByIsEnabled(PoundEnum isEnable);

    public  Page<PoundEntity> findAll(Specification specification, Pageable pageRequest);


    public List<PoundEntity> findAll(Specification specification);
}
