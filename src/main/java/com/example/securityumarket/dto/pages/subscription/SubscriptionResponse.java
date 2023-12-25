package com.example.securityumarket.dto.pages.subscription;

import lombok.Builder;

@Builder
public record SubscriptionResponse(
        Long id,
        String name,
        Integer countNewTransports,
        Boolean notificationStatus) {
}
