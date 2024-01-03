package com.example.securityumarket.dto.subscription.transport.removing;

import com.example.securityumarket.models.Transport;
import lombok.Builder;

@Builder
public record SubscriptionRemoveTransportRequest(
        Long transportId
) {
}
