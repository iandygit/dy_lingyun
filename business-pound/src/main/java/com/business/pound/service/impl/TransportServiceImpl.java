package com.business.pound.service.impl;


import com.business.pound.entity.PoundEntity;
import com.business.pound.entity.TransportEnetity;
import com.business.pound.repository.PoundRepository;
import com.business.pound.repository.TransportRepository;
import com.business.pound.service.TransportService;
import com.business.pound.util.OrderCodeFactory;
import com.business.pound.util.PoundEnum;
import com.business.pound.util.TransportEnum;
import com.business.pound.vo.PoundTransVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import com.lingyun.core.exception.ValidateCodeException;
@Service
public class TransportServiceImpl implements TransportService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransportRepository transportRepository;
    @Autowired
    private PoundRepository poundRepository;

    @Override
    public TransportEnetity save(TransportEnetity transportEnetity) {
        return transportRepository.saveAndFlush(transportEnetity);
    }

    @Override
    public void delete(TransportEnetity transportEnetity) {
        transportRepository.delete(transportEnetity);
    }

    @Override
    public List<TransportEnetity> getAll() {
        return transportRepository.findAll();
    }

    @Override
    public Page<TransportEnetity> getPage(Example<TransportEnetity> example, Pageable pageable) {

        return transportRepository.findAll(example,pageable);
    }


    public TransportEnetity getOne(Long id) {

        Optional<TransportEnetity> transportEnetity=transportRepository.findById(id);

        return transportEnetity.get();
    }

    @Override
    public void deleteById(Long id) {
        transportRepository.deleteById(id);
    }

    @Override
    public Page<PoundTransVo> getPageTransport(String transportNum, Pageable pageable) {

        if(StringUtils.isEmpty(transportNum)){

            return transportRepository.findAllTransport(pageable);
        }
        return transportRepository.findAllTransport(transportNum,pageable);
    }

    @Override
    public List<PoundTransVo> findAllList(String transportNum) {

         if(org.apache.commons.lang3.StringUtils.isEmpty(transportNum)){
             transportRepository.findAllList();
         }
        return transportRepository.findAllList(transportNum);
    }

    @Override
    @Transactional
    public int apporval(String[] ids, String status) {
        String cartNum="";
        TransportEnetity transportEnetity=new TransportEnetity();
        String transNum=OrderCodeFactory.getTransCode(1L);
         for(int i=0;i<ids.length;i++){

             Long idV=Long.valueOf(ids[i]);
             //transportEnetity.setPoundId(idV);

             PoundEntity poundEntity=poundRepository.getOne(idV);

             if(null==poundEntity ||null== poundEntity.getId()){//找不到数据

                 continue;
             }
             if(StringUtils.isEmpty(cartNum)){
                 cartNum=poundEntity.getCarNum();
             }else {
                 if(!cartNum.equals(poundEntity.getCarNum())){//不是同一个车的审批，放弃

                     throw new ValidateCodeException("车号不同，请检查数据合法性");
                     //continue;
                 }
             }
             poundEntity.setPoundStatus(PoundEnum.valueOf(status));//审批结果
             poundEntity.setTransportNum(transNum);

             transportEnetity.setPoundAccount(poundEntity.getPoundAccount());
        }
        //transportEnetity.setPoundNum(poundEntity.getPoundNum());


        transportEnetity.setStatus(TransportEnum.A);

        transportEnetity.setTransportNum(transNum);

        transportRepository.save(transportEnetity);
        return 1;
    }
}
