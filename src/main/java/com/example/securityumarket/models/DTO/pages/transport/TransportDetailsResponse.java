package com.example.securityumarket.models.DTO.pages.transport;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransportDetailsResponse(
        Boolean isFavorite,
        Integer countViews,
        LocalDateTime created,
        LocalDateTime lastUpdated) {}
