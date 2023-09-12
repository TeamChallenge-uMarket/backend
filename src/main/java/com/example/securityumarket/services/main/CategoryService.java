package com.example.securityumarket.services.main;

import com.example.securityumarket.dao.CategoryDAO;
import com.example.securityumarket.dao.ParentCategoryDAO;
import com.example.securityumarket.models.DTO.CategoryDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryDAO categoryDAO;
    private final ParentCategoryDAO parentCategoryDAO;

    private Category buildCategory(CategoryDTO categoryDTO) {
        Optional<ParentCategory> parentCategoryId = parentCategoryDAO.findById(categoryDTO.getParentCategoryId());

        return Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .parentCategory(parentCategoryId.orElseThrow(() -> new UsernameNotFoundException("Parent category not found")))
                .build();
    }

    @Transactional
    public ResponseEntity<String> addCategory(CategoryDTO categoryDTO) {
        Category category = buildCategory(categoryDTO);
        categoryDAO.save(category);
        return ResponseEntity.ok("Category added successfully");
    }

    public ResponseEntity<List<Category>> getCategoriesByParentId(Long id) {
        List<Category> categories = categoryDAO.findAllByParentCategoryId(id);
        System.out.println(categories.toString());
        if (categories == null || categories.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(categories);
        }
    }
}
