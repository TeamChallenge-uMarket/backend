package com.example.securityumarket.services.main;

import com.example.securityumarket.dao.ParentCategoryDAO;
import com.example.securityumarket.models.DTO.ParentCategoryDTO;
import com.example.securityumarket.models.entities.ParentCategory;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ParentCategoryService {
    private final ParentCategoryDAO parentCategoryDAO;

    private ParentCategory buildParentCategory(ParentCategoryDTO parentCategoryDTO) {
        return ParentCategory.builder()
                .name(parentCategoryDTO.getName())
                .description(parentCategoryDTO.getDescription())
                .build();
    }

    @Transactional
    public ResponseEntity<String> addParentCategory(ParentCategoryDTO parentCategoryDTO) {
        ParentCategory parentCategory = buildParentCategory(parentCategoryDTO);
        parentCategoryDAO.save(parentCategory);
        return ResponseEntity.ok("Parent category added successfully");
    }

}
