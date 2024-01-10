package com.example.securityumarket.dto.entities.user;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
public record TransportPageUserDetailsDto(
        String name,
        String photo,
        LocalDateTime createdAt) {
}
