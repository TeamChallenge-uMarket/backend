package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Product;
import com.example.securityumarket.models.entities.ProductCategory;
import com.example.securityumarket.models.search.FullSearchRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductCategoryDAO extends JpaRepository<ProductCategory, Long> {


    @Query("select p.product from ProductCategory p " +
            "where p.category.id in :#{#searchRequest.categoriesId} " +
            "and upper(p.product.name) like upper(concat('%', :#{#searchRequest.searchQuery}, '%')) " +
            "and upper(p.product.status) = upper(:#{#searchRequest.status.name()}) " +
            "order by " +
            "   case when lower(:#{#searchRequest.orderBy.name()}) = 'created' and lower(:#{#searchRequest.sortBy.name()}) = 'asc' then p.product.created end asc, " +
            "   case when lower(:#{#searchRequest.orderBy.name()}) = 'created' and lower(:#{#searchRequest.sortBy.name()}) = 'desc' then p.product.created end desc, " +
            "   case when lower(:#{#searchRequest.orderBy.name()}) = 'price' and  lower(:#{#searchRequest.sortBy.name()}) = 'asc' then p.product.price end asc, " +
            "   case when lower(:#{#searchRequest.orderBy.name()}) = 'price' and lower(:#{#searchRequest.sortBy.name()}) = 'desc' then p.product.price end desc")
    List<Product> findProductsByNameAndCategory(
            @Param("searchRequest") FullSearchRequest searchRequest,
            PageRequest pageable);
}
