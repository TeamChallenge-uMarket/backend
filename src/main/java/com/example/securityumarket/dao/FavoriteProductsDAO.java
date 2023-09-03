package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.FavoriteProducts;
import com.example.securityumarket.models.entities.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FavoriteProductsDAO extends JpaRepository<FavoriteProducts, Long> {

    @Query("select f.product from FavoriteProducts f where f.product = :product")
    Product findProduct(@Param("product") Product product);

    @Query("select f.product from FavoriteProducts f")
    List<Product> getMyFavorite(PageRequest pageable);

    @Modifying
    @Transactional
    @Query("delete from FavoriteProducts f where f.product.id = :myFavProductId and f.user.id = :userId")
    void deleteMyFavorite(@Param("myFavProductId") Long myFavProductId, @Param("userId") Long userId);
}
