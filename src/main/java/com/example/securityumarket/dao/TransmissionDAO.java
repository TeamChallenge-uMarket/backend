package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransmissionDAO  extends JpaRepository<Transmission, Long> {
}
