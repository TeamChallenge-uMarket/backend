package com.example.securityumarket.dao;

import com.example.securityumarket.models.HiddenAd;
import com.example.securityumarket.models.Transport;
import com.example.securityumarket.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HiddenAdDAO extends JpaRepository<HiddenAd, Long> {
    List<HiddenAd> findAllByUser(Users users);

    Optional<HiddenAd> findByUserAndTransport(Users user, Transport transport);
}
