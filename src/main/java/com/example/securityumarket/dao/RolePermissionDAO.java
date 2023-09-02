package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionDAO extends JpaRepository<RolePermission, Long> {
}
