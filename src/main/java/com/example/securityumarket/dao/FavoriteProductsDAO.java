package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.FavoriteProducts;
import com.example.securityumarket.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteProductsDAO extends JpaRepository<FavoriteProducts, Long> {

    @Query("select f.product from FavoriteProducts f where f.product = :product")
    Product findProduct(@Param("product") Product product);
}
