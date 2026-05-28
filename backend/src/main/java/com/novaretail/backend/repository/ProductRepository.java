package com.novaretail.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.novaretail.backend.entity.Products;

public interface ProductRepository extends JpaRepository<Products, Long> {

}