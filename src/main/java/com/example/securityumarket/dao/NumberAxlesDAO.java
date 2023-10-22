package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.NumberAxles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberAxlesDAO  extends JpaRepository<NumberAxles, Long> {
}