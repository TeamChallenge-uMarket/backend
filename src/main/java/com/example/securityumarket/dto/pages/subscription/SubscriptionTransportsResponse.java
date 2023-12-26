package com.example.securityumarket.dto.pages.subscription;

import com.example.securityumarket.dto.pages.catalog.response.ResponseSearch;
import lombok.Builder;

import java.util.List;

@Builder
public record SubscriptionTransportsResponse (
        List<ResponseSearch> unseenTransportList,
        List<ResponseSearch> viewedTransportList){
}
