package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.TransportView;
import com.example.securityumarket.models.entities.Users;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransportViewDAO extends JpaRepository<TransportView, Long> {

    @Query("select cw.transport from TransportView cw group by cw.transport order by count(cw.transport) desc")
    Optional<List<Transport>> findPopularTransport(PageRequest pageRequest);

    @Query("select cw.transport from TransportView cw where cw.user = :user order by cw.lastUpdate desc")
    Optional<List<Transport>> findViewedCarsByRegisteredUser(@Param("user") Users user, PageRequest pageRequest);
}
