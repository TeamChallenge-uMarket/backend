package com.example.securityumarket.dto.entities.user;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransportPageUserDetailsDto(
        String name,
        String photo,
        LocalDateTime createdAt) {
}
