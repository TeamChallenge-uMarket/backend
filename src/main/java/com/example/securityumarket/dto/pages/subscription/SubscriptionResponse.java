package com.example.securityumarket.dto.pages.subscription;

import com.example.securityumarket.dto.pages.catalog.request.RequestSearch;
import lombok.Builder;

@Builder
public record SubscriptionResponse(
        Long id,
        String name,
        RequestSearch requestSearch,
        Integer countNewTransports,
        Boolean notificationStatus) {
}
