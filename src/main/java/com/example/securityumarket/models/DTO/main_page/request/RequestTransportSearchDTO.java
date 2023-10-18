package com.example.securityumarket.models.DTO.main_page.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestTransportSearchDTO {
    private Long typeId;
    private Long brandId;
    private List<Long> modelId;
    private List<Long> regionId;
    private OrderBy orderBy;
    private SortBy sortBy;


    public enum SortBy {
        ASC, DESC
    }

    public enum OrderBy {
        CREATED, PRICE
    }
}
