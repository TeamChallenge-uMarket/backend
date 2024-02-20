package com.example.securityumarket.dto.pages.catalog.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SearchResponse(

        List<TransportSearchResponse> transportSearchResponse,
        Long total) {

}
