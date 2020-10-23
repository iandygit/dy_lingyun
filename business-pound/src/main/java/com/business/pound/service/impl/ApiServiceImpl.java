package com.business.pound.service.impl;

import com.business.pound.entity.ApiEentity;
import com.business.pound.repository.ApiRepository;
import com.business.pound.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ApiServiceImpl implements ApiService {
    @Autowired
    private ApiRepository apiRepository;
    @Override
    public ApiEentity save(ApiEentity apiEentity) {
        return apiRepository.save(apiEentity);
    }

    @Override
    public void delete(ApiEentity apiEentity) {
        apiRepository.delete(apiEentity);
    }

    @Override
    public List<ApiEentity> getAll() {
        return apiRepository.findAll();
    }

    @Override
    public Page<ApiEentity> getPage(Example<ApiEentity> example, Pageable pageable) {
        return apiRepository.findAll(example,pageable);
    }

    @Override
    public ApiEentity getOne(Long id) {
        return apiRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        apiRepository.deleteById(id);
    }
}
