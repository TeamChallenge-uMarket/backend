package com.example.securityumarket.models.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.example.securityumarket.models.entities.Product.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullSearchRequest {
    private String searchQuery;
    private List<Long> categoriesId;
    private Status status;
    private OrderBy orderBy;
    private SortBy sortBy;


    public enum SortBy {
        ASC, DESC
    }
    public enum OrderBy {
        CREATED, PRICE
    }
}
