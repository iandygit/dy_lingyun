package com.business.pound.service;

import com.business.pound.entity.TransportEnetity;
import com.business.pound.vo.PoundTransVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransportService  extends BaseService<TransportEnetity>{

    public Page<PoundTransVo> getPageTransport(String transportNum, Pageable pageable);


    public List<PoundTransVo> findAllList(String poundNum);
}
