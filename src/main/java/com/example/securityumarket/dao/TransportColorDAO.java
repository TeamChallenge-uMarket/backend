package com.example.securityumarket.dao;

import com.example.securityumarket.models.TransportColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransportColorDAO extends JpaRepository<TransportColor, Long> {
}
