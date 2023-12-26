package com.example.securityumarket.dto.pages.subscription;

import com.example.securityumarket.dto.pages.catalog.request.RequestSearchDTO;
import lombok.Builder;

@Builder
public record SubscriptionResponse(
        Long id,
        String name,
        RequestSearchDTO requestSearchDTO,
        Integer countNewTransports,
        Boolean notificationStatus) {
}
