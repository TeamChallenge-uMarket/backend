package com.example.securityumarket.dao;

import com.example.securityumarket.models.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransmissionDAO  extends JpaRepository<Transmission, Long> {
}
