package com.example.securityumarket.dto.subscription.transport.adding;

import lombok.Builder;

@Builder
public record SubscriptionAddTransportRequest(
        Long transportId,
        Long subscriptionId) {
}
