package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportTypeDAO extends JpaRepository<TransportType, Long> {
}
