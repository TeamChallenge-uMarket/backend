package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.TransportGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransportGalleryDAO extends JpaRepository<TransportGallery, Long> {

    @Query("select tg.url from TransportGallery tg where tg.transport.id = :transportId and tg.isMain = true")
    Optional<String> findMainFileByTransport(@Param("transportId") long transportId);

    @Query("SELECT tg FROM TransportGallery tg WHERE tg.transport.id = :transportId")
    Optional<List<TransportGallery>> findAllByTransportId(@Param("transportId")Long id);

    @Query("select tg from TransportGallery tg where tg.transport.id = :transportId and tg.isMain = true")
    Optional<TransportGallery> findMainTransportGalleryByTransportId(@Param("transportId")Long id);

    @Modifying
    @Query("DELETE FROM TransportGallery tg WHERE tg.id = :id")
    void deleteTransportGalleryById(@Param("id") Long transportGalleryId);
}
