package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.CarGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarGalleryDAO extends JpaRepository<CarGallery, Long> {
    @Query("select c.urlSmall from CarGallery c where c.car.id = :carId and c.isMain = true")
    String findSmallMainPic(@Param("carId") long carId);
}
