package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Product;
import com.example.securityumarket.models.entities.ProductCategory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductCategoryDAO extends JpaRepository<ProductCategory, Long> {


    @Query("select p.product from ProductCategory p " +
            "where p.category.id in :categories " +
            "and upper(p.product.name) like upper(concat('%', :searchQuery, '%')) " +
            "and upper(p.product.status) = upper(:status) " +
            "order by " +
            "   case when lower(:orderBy) = 'created' and lower(:sortBy) = 'asc' then p.product.created end asc, " +
            "   case when lower(:orderBy) = 'created' and lower(:sortBy) = 'desc' then p.product.created end desc, " +
            "   case when lower(:orderBy) = 'price' and  lower(:sortBy) = 'asc' then p.product.price end asc, " +
            "   case when lower(:orderBy) = 'price' and lower(:sortBy) = 'desc' then p.product.price end desc")
    List<Product> findAllByNameAndCategory(
            @Param("categories") List<Long> categories,
            @Param("searchQuery") String searchQuery,
            @Param("status") String status,
            @Param("orderBy") String orderBy,
            @Param("sortBy") String sortBy,

            PageRequest pageable);
}
