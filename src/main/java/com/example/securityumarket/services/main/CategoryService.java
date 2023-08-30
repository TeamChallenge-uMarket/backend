package com.example.securityumarket.services.main;

import com.example.securityumarket.dao.CategoryDAO;
import com.example.securityumarket.models.DTO.CategoryDTO;
import com.example.securityumarket.models.entities.Category;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryDAO categoryDAO;

    private Category buildCategory(CategoryDTO categoryDTO) {
        return Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .parentCategory(categoryDTO.getParentCategory())
                .build();
    }

    @Transactional
    public ResponseEntity<String> addCategory(CategoryDTO categoryDTO) {
        Category category = buildCategory(categoryDTO);
        categoryDAO.save(category);
        return ResponseEntity.ok("Category added successfully");
    }
}
