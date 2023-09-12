package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.CarGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductGalleryDAO extends JpaRepository<CarGallery, Long> {

    @Query("select p from CarGallery p where p.product.id = ?1 and p.isMain = ?2")
    CarGallery findByProductIdAndIsMain(Long id, Boolean isMain);
}
