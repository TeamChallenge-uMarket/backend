package com.example.securityumarket.models.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchNameCategoryRequest {
    private String searchField;
    private List<Long> categoriesId;
}
