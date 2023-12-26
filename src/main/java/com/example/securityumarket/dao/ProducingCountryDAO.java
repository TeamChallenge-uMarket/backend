package com.example.securityumarket.dao;

import com.example.securityumarket.models.ProducingCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProducingCountryDAO  extends JpaRepository<ProducingCountry, Long> {
}