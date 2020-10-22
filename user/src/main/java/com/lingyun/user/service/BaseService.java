package com.lingyun.user.service;

import com.lingyun.user.entity.TransportEnetity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<T> {

    public T save(T t);

    public  void delete(T t);

    public List<T> getAll();

    public Page<T> getPage(Example<T> example, Pageable pageable);

    public T getOne(Long id);

    public  void deleteById(Long id);
}
