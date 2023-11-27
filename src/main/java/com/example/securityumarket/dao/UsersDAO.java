package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersDAO extends JpaRepository<Users, Long> {

    Optional<Users> findAppUserByEmail(String email);

    boolean existsUsersByEmail(String email);

    @Query("SELECT u FROM Users u WHERE u.phone = :phone")
    Optional<Users> findUserByPhone(@Param("phone")String phone);

    @Modifying
    @Query("DELETE FROM Users u WHERE u.active = false AND u.created < :date")
    void deleteByActiveFalseAndCreatedDateBefore(@Param("date")LocalDateTime created_date);

    Optional<List<Users>> findAllByStatus(Users.Status status);
}
