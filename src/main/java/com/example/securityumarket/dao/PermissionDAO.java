package com.example.securityumarket.dao;

import com.example.securityumarket.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionDAO extends JpaRepository<Permission, Long> {
}
