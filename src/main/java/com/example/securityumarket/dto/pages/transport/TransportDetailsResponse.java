package com.example.securityumarket.dto.pages.transport;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransportDetailsResponse(
        Long id,
        Boolean isFavorite,
        Integer countViews,
        LocalDateTime created,
        LocalDateTime lastUpdated) {}
