package com.business.pound.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.business.pound.entity.PoundEntity;
import com.business.pound.repository.PoundRepository;
import com.business.pound.service.PoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
}
