package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.WheelConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WheelConfigurationDAO extends JpaRepository<WheelConfiguration, Long> {
}