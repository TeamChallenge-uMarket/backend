package com.example.securityumarket.dao;

import com.example.securityumarket.models.UserRole;
import com.example.securityumarket.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleDAO extends JpaRepository<UserRole, Long> {
    Optional<List<UserRole>> findAllByUser(Users user);
}
