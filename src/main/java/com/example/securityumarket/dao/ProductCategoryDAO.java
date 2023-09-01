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
    @Query("select p.product from ProductCategory p where p.category in :categories order by p.product.created desc")
    List<Product> findProductsByCategories(@Param("categories") List<Category> categories, PageRequest pageable);

    @Query("select p.product from ProductCategory p " +
            "join p.product p2 " +
            "where p.category.id in :categories " +
            "and p2.name like lower(concat('%', :name, '%')) ")
    List<Product> findAllByNameAndCategory(@Param("categories") List<Long> categories, @Param("name") String name, PageRequest pageable);
}
