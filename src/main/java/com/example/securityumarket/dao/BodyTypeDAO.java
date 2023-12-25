package com.example.securityumarket.dao;

import com.example.securityumarket.models.BodyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyTypeDAO  extends JpaRepository<BodyType, Long>, JpaSpecificationExecutor<BodyType> {
}
