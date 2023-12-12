package com.example.securityumarket.models.DTO.pages.transport;

import lombok.Builder;

@Builder
public record TransportDetailsResponse(
  Boolean isFavorite,
  Integer countViews) {}
