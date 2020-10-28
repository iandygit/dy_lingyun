package com.business.pound.service.impl;


import com.business.pound.entity.PoundEntity;
import com.business.pound.entity.TransportEnetity;
import com.business.pound.repository.PoundRepository;
import com.business.pound.repository.TransportRepository;
import com.business.pound.service.TransportService;
import com.business.pound.util.OrderCodeFactory;
import com.business.pound.util.PoundEnum;
import com.business.pound.vo.PoundTransVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public List<PoundTransVo> findAllList(String poundAccount) {


        return transportRepository.findAllList(poundAccount);
    }

    @Override
    @Transactional
    public int apporval(String[] ids, String status) {

         for(int i=0;i<ids.length;i++){
             TransportEnetity transportEnetity=new TransportEnetity();
             Long idV=Long.valueOf(ids[i]);
             transportEnetity.setPoundId(idV);
             if(null!=status &&status.equals("A")){
                 transportEnetity.setStatus(PoundEnum.APPORVAL_A);
             }else {
                 transportEnetity.setStatus(PoundEnum.APPORVAL_B);
             }
             PoundEntity poundEntity=poundRepository.getOne(idV);
             if(null==poundEntity ||null== poundEntity.getId()){
                 continue;
             }
             transportEnetity.setPoundNum(poundEntity.getPoundNum());
             transportEnetity.setPoundAccount(poundEntity.getPoundAccount());



             String transNum=OrderCodeFactory.getTransCode(1L);
             transportEnetity.setTransportNum(transNum);

             transportRepository.save(transportEnetity);
        }
        return 1;
    }
}
