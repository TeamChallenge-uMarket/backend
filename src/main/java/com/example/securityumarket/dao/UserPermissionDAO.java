package com.example.securityumarket.dao;

import com.example.securityumarket.models.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPermissionDAO extends JpaRepository<UserPermission, Long> {
}
