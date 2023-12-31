package com.example.securityumarket.dao;

import com.example.securityumarket.models.DriveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DriveTypeDAO  extends JpaRepository<DriveType, Long>, JpaSpecificationExecutor<DriveType> {
}
