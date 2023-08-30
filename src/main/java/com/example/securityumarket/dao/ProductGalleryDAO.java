package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.ProductGallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductGalleryDAO extends JpaRepository<ProductGallery, Long> {
}
