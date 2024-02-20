package com.example.securityumarket.dto.filters.request;

import lombok.Builder;

@Builder
public record UpdateFilterParametersRequest(
        String key,
        Object value,

        String field) {
}
