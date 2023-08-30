package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleDAO extends JpaRepository<UserRole, Long> {
}
