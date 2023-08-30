package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Product;
import com.example.securityumarket.models.entities.Users;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface ProductDAO extends JpaRepository<Product, Long> {
    List<Product> findAllByUserId(Long id);

    @Query("select p from Product p where upper(p.name) like upper(concat('%', :name, '%'))")
    List<Product> findProductByNameIsContainingIgnoreCase(@Param("name") String name, PageRequest pageable);
}
