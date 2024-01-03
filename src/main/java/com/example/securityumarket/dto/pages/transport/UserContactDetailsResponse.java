package com.example.securityumarket.dto.pages.transport;

import lombok.Builder;

@Builder
public record UserContactDetailsResponse(
        String email,
        String phone) {
}
