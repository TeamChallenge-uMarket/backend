package com.example.securityumarket.models.DTO.pages.catalog.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RequestFilterParam {

    private Long transportTypeId;

    private List<Long> transportBrandsId;
}