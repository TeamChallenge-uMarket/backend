package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPermissionDAO extends JpaRepository<UserPermission, Long> {
}
