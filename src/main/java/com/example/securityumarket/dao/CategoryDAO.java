package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryDAO extends JpaRepository<Category, Long> {
    List<Category> findAllByParentCategoryId(Long id);

    @Query("select c from Category c where c.id in :categories")
    List<Category> findCategoryByIdIn(@Param("categories") List<Long> categories);
}
