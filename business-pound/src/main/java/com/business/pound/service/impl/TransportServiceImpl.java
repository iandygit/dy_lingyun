package com.business.pound.service.impl;


import com.business.pound.entity.TransportEnetity;
import com.business.pound.repository.TransportRepository;
import com.business.pound.service.TransportService;
import com.business.pound.vo.PoundTransVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class TransportServiceImpl implements TransportService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransportRepository transportRepository;

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
    public List<PoundTransVo> findAllList() {


        return transportRepository.findAllList();
    }
}
