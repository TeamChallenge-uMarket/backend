package com.example.securityumarket.models.DTO.main_page.request;

import com.example.securityumarket.models.DTO.main_page.request.constants.Sort;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestCarSearchDTO {
    private long typeId;
    private long brandId;
    private long modelId;
    private long cityId;
    private Sort sort;
}
