package com.example.securityumarket.models.DTO.main_page.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestTransportSearchDTO {
    private long typeId;
    private long brandId;
    private long modelId;
    private long regionId;
    private OrderBy orderBy;
    private SortBy sortBy;


    public enum SortBy {
        ASC, DESC
    }

    public enum OrderBy {
        CREATED, PRICE
    }
}
