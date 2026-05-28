package com.novaretail.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.novaretail.backend.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

}