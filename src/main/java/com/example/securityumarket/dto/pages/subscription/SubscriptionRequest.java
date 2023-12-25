package com.example.securityumarket.dto.pages.subscription;

import lombok.Builder;

@Builder
public record SubscriptionRequest(
        String name,
        Boolean notificationEnabled) {
}