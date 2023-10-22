package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.TransportView;
import com.example.securityumarket.models.entities.Users;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportViewDAO extends JpaRepository<TransportView, Long> {

    @Query("SELECT cw.transport FROM TransportView cw GROUP BY cw.transport ORDER BY count(cw.transport) DESC LIMIT 54")
    Optional<List<Transport>> findPopularTransport();

    @Query("SELECT cw.transport FROM TransportView cw WHERE cw.user = :user ORDER BY cw.lastUpdate DESC ")
    Optional<List<Transport>> findViewedCarsByRegisteredUser(@Param("user") Users user);

    @Query("SELECT cw.transport FROM TransportView cw WHERE cw.transport.transportModel.transportTypeBrand.transportType.id=:id GROUP BY cw.transport ORDER BY count(cw.transport) DESC LIMIT 20")
    Optional<List<Transport>> findPopularTransportByTypeId(long id);
}
