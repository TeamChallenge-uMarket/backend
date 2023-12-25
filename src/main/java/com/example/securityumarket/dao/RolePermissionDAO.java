package com.example.securityumarket.dao;

import com.example.securityumarket.models.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionDAO extends JpaRepository<RolePermission, Long> {
}
