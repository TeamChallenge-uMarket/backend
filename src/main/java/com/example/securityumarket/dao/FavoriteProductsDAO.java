package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.FavoriteProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteProductsDAO extends JpaRepository<FavoriteProducts, Long> {
}
