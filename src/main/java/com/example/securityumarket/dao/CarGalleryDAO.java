package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.CarGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarGalleryDAO extends JpaRepository<CarGallery, Long> {

    @Query("select c.url from CarGallery c where c.car.id = :carId and c.isMain = true")
    Optional<String> findMainFileByCar(@Param("carId") long carId);

    @Query("SELECT cg FROM CarGallery cg WHERE cg.imageName IN (:imageNames)")
    Optional<List<CarGallery>> findCarGalleriesByImageNames(@Param("imageNames") List<String> imageNames);
}
