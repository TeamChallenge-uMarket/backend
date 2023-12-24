package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.BodyType;
import com.example.securityumarket.models.entities.City;
import com.example.securityumarket.models.entities.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BodyTypeDAO  extends JpaRepository<BodyType, Long>, JpaSpecificationExecutor<BodyType> {
}
