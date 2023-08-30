package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionDAO extends JpaRepository<Permission, Long> {
}
