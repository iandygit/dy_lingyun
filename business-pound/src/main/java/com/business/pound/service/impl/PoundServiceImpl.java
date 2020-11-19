package com.business.pound.service.impl;

import com.business.pound.entity.PoundEntity;
import com.business.pound.repository.PoundRepository;
import com.business.pound.service.PoundService;
import com.business.pound.util.PoundEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@Service
public class PoundServiceImpl  implements PoundService {
   @Autowired
   private PoundRepository poundRepository;

    @Override
    public PoundEntity save(PoundEntity poundEntity) {
        return poundRepository.saveAndFlush(poundEntity);
    }

    @Override
    public void delete(PoundEntity poundEntity) {

        poundRepository.delete(poundEntity);
    }

    @Override
    public List<PoundEntity> getAll() {
        return poundRepository.findAll();
    }

    @Override
    public Page<PoundEntity> getPage(Example<PoundEntity> example, Pageable pageable) {

        return poundRepository.findAll(example,pageable);
    }

    @Override
    public PoundEntity getOne(Long id) {
        return poundRepository.getOne(id);
    }

    @Override
    public void deleteById(Long id) {
        poundRepository.deleteById(id);
    }


    @Override
    public List<PoundEntity> findAllByPoundNum(String poundNum) {
        return poundRepository.findAllByPoundNum(poundNum);
    }

    @Override
    public List<PoundEntity> findAllByPoundAccount(String poundAccount) {


        return poundRepository.findAllByPoundAccountAndIsEnabled(poundAccount,PoundEnum.Y);
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteByIdNumAccount(Long id, String num, String account) {
        Optional optional=poundRepository.findById(id);
        if(!optional.isPresent()){
            return ResponseEntity.ok("找不到数据");
        }else {
            PoundEntity entity= (PoundEntity) optional.get();
            if(!num.equals(entity.getPoundNum())){
                return ResponseEntity.ok("数据不合法");
            }
            if(!account.equals(entity.getPoundAccount())){
                return ResponseEntity.ok("数据不合法");
            }
            entity.setIsEnabled(PoundEnum.N);
        }

        return ResponseEntity.ok("操作成功");
    }

    @Override
    public List<PoundEntity> findAllByIsEnabled(PoundEnum isEnable) {

        return poundRepository.findAllByIsEnabled(isEnable);
    }

    @Override
    public List<PoundEntity> getExportResult(String poundAccount, PoundEnum isEnable, String startTime, String endTime) {


        Specification specification = new Specification(){

            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //增加筛选条件
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("isEnabled"), isEnable));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                if(StringUtils.isNotBlank(poundAccount)){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("poundAccount"), poundAccount));
                }
                try {
                    //起始日期
                    if (startTime != null && !startTime.trim().equals("")) {

                        predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class),startTime ));
                    }
                    //结束日期
                    if (endTime != null && !endTime.trim().equals("")) {
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(format.parse(endTime));
                        calendar.add(calendar.DATE,1); //把日期往后增加一天,整数  往后推,负数往前移动
                        predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(String.class),format.format(calendar.getTime())));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return predicate;
            }
        };

        List<PoundEntity> list= poundRepository.findAll(specification);
        return list;
    }

    @Override
    public Page<PoundEntity> findAll(Specification specification,Pageable pageRequest) {

        return poundRepository.findAll(specification,pageRequest);
    }
}
