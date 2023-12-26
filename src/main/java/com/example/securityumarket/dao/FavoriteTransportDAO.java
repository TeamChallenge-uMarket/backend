package com.example.securityumarket.dao;

import com.example.securityumarket.models.FavoriteTransport;
import com.example.securityumarket.models.Transport;
import com.example.securityumarket.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteTransportDAO extends JpaRepository<FavoriteTransport, Long> {
    @Query("SELECT ft.transport FROM FavoriteTransport ft WHERE ft.user = :user ORDER BY ft.created DESC")
    Optional<List<Transport>> findFavorites(@Param("user") Users user);

   Boolean existsByUserAndTransport(Users users, Transport transport);

    @Modifying
    @Query("DELETE FROM FavoriteTransport ft WHERE ft.user = :user AND ft.transport = :transport")
    void deleteByUserAndTransport(@Param("user") Users user, @Param("transport") Transport transport);


    Integer countAllByTransport(Transport transport);
}
