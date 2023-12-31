package com.example.securityumarket.dto.subscription.transport.adding;

import com.example.securityumarket.models.Subscription;
import com.example.securityumarket.models.Transport;
import lombok.Builder;

@Builder
public record SubscriptionAddTransportRequest(
        Long transportId,
        Long subscriptionId) {
}
