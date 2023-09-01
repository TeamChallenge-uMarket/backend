package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Category;
import com.example.securityumarket.models.entities.Product;
import com.example.securityumarket.models.entities.ProductCategory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductCategoryDAO extends JpaRepository<ProductCategory, Long> {
    @Query("select p.product from ProductCategory p where p.category in :categories")
    List<Product> findProductsByCategories(@Param("categories") List<Category> categories, PageRequest pageable);
}
