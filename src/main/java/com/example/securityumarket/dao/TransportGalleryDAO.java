package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.TransportGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportGalleryDAO extends JpaRepository<TransportGallery, Long> {

    @Query("select c.url from TransportGallery c where c.transport.id = :transportId and c.isMain = true")
    Optional<String> findMainFileByTransport(@Param("transportId") long transportId);

    @Query("SELECT cg FROM TransportGallery cg WHERE cg.imageName IN (:imageNames)")
    Optional<List<TransportGallery>> findCarGalleriesByImageNames(@Param("imageNames") List<String> imageNames);
}
