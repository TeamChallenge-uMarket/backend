package com.example.securityumarket.dao;

import com.example.securityumarket.models.Transport;
import com.example.securityumarket.models.TransportView;
import com.example.securityumarket.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransportViewDAO extends JpaRepository<TransportView, Long> {

    Optional<TransportView> findByUserAndTransport(Users users, Transport transport);

    Integer countDistinctByTransport(Transport transport);

    @Modifying
    @Query("UPDATE TransportView tv SET tv.lastUpdate = :localDateTime WHERE tv.id= :id")
    void updateLastUpdated(@Param("id")Long transportViewId, @Param("localDateTime") LocalDateTime localDateTime);
}
