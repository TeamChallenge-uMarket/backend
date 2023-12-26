package com.example.securityumarket.dao;

import com.example.securityumarket.models.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportTypeDAO extends JpaRepository<TransportType, Long> {
}
