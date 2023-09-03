package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Product;
import com.example.securityumarket.models.search.FullSearchRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Long> {
    List<Product> findAllByUserId(Long id);

    Product findProductById(Long id);

    @Query("select p from Product p " +
            "where upper(p.name) like upper(concat('%', :#{#searchRequest.searchQuery}, '%')) " +
            "and upper(p.status) = upper(:#{#searchRequest.status.name()}) " +
            "order by " +
            "   case when lower(:#{#searchRequest.orderBy.name()}) = 'created' and lower(:#{#searchRequest.sortBy.name()}) = 'asc' then p.created end asc, " +
            "   case when lower(:#{#searchRequest.orderBy.name()}) = 'created' and lower(:#{#searchRequest.sortBy.name()}) = 'desc' then p.created end desc, " +
            "   case when lower(:#{#searchRequest.orderBy.name()}) = 'price' and  lower(:#{#searchRequest.sortBy.name()}) = 'asc' then p.price end asc, " +
            "   case when lower(:#{#searchRequest.orderBy.name()}) = 'price' and lower(:#{#searchRequest.sortBy.name()}) = 'desc' then p.price end desc")
    List<Product> findProductsByName(
            @Param("searchRequest") FullSearchRequest searchRequest,
            PageRequest pageable);

}
