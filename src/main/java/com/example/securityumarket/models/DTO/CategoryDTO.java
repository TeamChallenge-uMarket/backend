package com.example.securityumarket.models.DTO;

import lombok.Data;

@Data
public class CategoryDTO {
    private String name;
    private String description;
    private Long parentCategoryId;
}
