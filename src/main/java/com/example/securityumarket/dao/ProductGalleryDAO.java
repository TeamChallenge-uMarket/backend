package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.ProductGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductGalleryDAO extends JpaRepository<ProductGallery, Long> {

    @Query("select p from ProductGallery p where p.product.id = ?1 and p.isMain = ?2")
    ProductGallery findByProductIdAndIsMain(Long id, Boolean isMain);
}
