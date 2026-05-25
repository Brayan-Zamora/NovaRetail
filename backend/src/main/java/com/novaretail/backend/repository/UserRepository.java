package com.novaretail.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.novaretail.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}