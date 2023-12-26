package com.example.securityumarket.dto.pages.subscription;

import com.example.securityumarket.dto.pages.catalog.response.ResponseSearchDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record SubscriptionTransportsResponse (
        List<ResponseSearchDTO> unseenTransportList,
        List<ResponseSearchDTO> viewedTransportList){
}
