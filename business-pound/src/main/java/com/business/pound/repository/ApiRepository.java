package com.business.pound.repository;

import com.business.pound.entity.ApiEentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRepository extends JpaRepository<ApiEentity,Long> {
}
