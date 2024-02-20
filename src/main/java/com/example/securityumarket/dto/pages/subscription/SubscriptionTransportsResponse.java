package com.example.securityumarket.dto.pages.subscription;

import com.example.securityumarket.dto.pages.catalog.response.TransportSearchResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record SubscriptionTransportsResponse (
        List<TransportSearchResponse> unseenTransportList,
        List<TransportSearchResponse> viewedTransportList){
}
