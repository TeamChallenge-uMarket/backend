package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.BodyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyTypeDAO  extends JpaRepository<BodyType, Long> {
}
