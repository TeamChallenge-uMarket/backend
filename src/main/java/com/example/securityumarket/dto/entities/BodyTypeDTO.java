package com.example.securityumarket.dto.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BodyTypeDTO {

    private long bodyTypeId;

    private String bodyType;
}
