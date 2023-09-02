package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Long> {
    List<Product> findAllByUserId(Long id);

    @Query("select p from Product p " +
            "where upper(p.name) like upper(concat('%', :searchQuery, '%')) " +
            "and upper(p.status) = upper(:status) " +
            "order by " +
            "   case when lower(:orderBy) = 'created' and lower(:sortBy) = 'asc' then p.created end asc, " +
            "   case when lower(:orderBy) = 'created' and lower(:sortBy) = 'desc' then p.created end desc, " +
            "   case when lower(:orderBy) = 'price' and  lower(:sortBy) = 'asc' then p.price end asc, " +
            "   case when lower(:orderBy) = 'price' and lower(:sortBy) = 'desc' then p.price end desc")
    List<Product> findProductByName(
            @Param("searchQuery") String searchQuery,
            @Param("status") String status,
            @Param("orderBy") String orderBy,
            @Param("sortBy") String sortBy,
            PageRequest pageable);

}
