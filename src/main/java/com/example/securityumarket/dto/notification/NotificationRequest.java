package com.example.securityumarket.dto.notification;

import lombok.Builder;

@Builder
public record NotificationRequest (
        Long userId,
        String email,
        Long subscriptionId,
        String subscriptionName,
        String transportDetail,
        Long transportId) {
}
