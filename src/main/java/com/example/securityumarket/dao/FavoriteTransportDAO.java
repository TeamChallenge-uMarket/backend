package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.FavoriteTransport;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.Users;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteTransportDAO extends JpaRepository<FavoriteTransport, Long> {
    @Query("select ft.transport from FavoriteTransport ft where ft.user = :user order by ft.created desc")
    Optional<List<Transport>> findFavorites(@Param("user") Users user, PageRequest pageRequest);
}
