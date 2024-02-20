package com.example.securityumarket.dto.pages.hidden.response;

import com.example.securityumarket.dto.pages.catalog.response.TransportSearchResponse;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record HiddenTransportResponse(
        Long id,
        TransportSearchResponse transport,
        LocalDateTime created) {
}
