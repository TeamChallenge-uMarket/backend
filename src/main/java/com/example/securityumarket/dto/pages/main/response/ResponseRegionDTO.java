package com.example.securityumarket.dto.pages.main.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseRegionDTO {

    long regionId;

    String region;
}
