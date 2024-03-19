package com.example.securityumarket.dto.subscription.transport.removing;

import lombok.Builder;

@Builder
public record SubscriptionRemoveTransportRequest(
        Long transportId
) {
}
