package com.example.securityumarket.models.DTO;

import com.example.securityumarket.models.entities.ParentCategory;
import lombok.Data;

@Data
public class CategoryDTO {
    private String name;
    private String description;
    private Long parentCategoryId;
}
