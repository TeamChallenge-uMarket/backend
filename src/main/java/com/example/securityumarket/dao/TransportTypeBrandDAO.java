package com.example.securityumarket.dao;

import com.example.securityumarket.models.TransportTypeBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportTypeBrandDAO extends JpaRepository<TransportTypeBrand, Long> {
}
