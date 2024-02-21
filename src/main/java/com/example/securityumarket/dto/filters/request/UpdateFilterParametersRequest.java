package com.example.securityumarket.dto.filters.request;

import lombok.Builder;

@Builder
public record UpdateFilterParametersRequest(
        Long transportTypeId) {
}
