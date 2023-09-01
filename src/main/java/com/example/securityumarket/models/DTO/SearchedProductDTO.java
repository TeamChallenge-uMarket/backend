package com.example.securityumarket.models.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SearchedProductDTO {

    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private BigDecimal price;
}
