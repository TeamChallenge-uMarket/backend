package com.example.securityumarket.dto.pages.hidden.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record HiddenUserResponse(
        Long id,
        Long userId,
        String userPhoto,
        LocalDateTime created) {
}
