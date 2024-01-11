package com.example.securityumarket.dao;

import com.example.securityumarket.models.HiddenUser;
import com.example.securityumarket.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HiddenUserDAO extends JpaRepository<HiddenUser, Long>{
    List<HiddenUser> findAllByUser(Users user);

    Optional<HiddenUser> findByUserAndHiddenUser(Users user, Users hiddenUser);
}
