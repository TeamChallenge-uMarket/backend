package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.ProducingCountry;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProducingCountryDAO  extends JpaRepository<ProducingCountry, Long> {
}