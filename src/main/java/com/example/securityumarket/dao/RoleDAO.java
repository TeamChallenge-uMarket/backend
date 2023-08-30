package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role, Long> {
}
