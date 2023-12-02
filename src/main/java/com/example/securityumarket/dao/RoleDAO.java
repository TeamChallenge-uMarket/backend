package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {
    Role findByName (Role.Roles roles);
}
