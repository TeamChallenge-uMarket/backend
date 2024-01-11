package com.example.securityumarket.dto.pages.hidden.response;

import com.example.securityumarket.dto.pages.catalog.response.ResponseSearch;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record HiddenTransportResponse(
        Long id,
        ResponseSearch transport,
        LocalDateTime created) {
}
