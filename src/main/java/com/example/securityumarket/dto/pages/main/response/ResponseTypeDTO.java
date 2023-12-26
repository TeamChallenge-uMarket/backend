package com.example.securityumarket.dto.pages.main.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseTypeDTO {

    private long typeId;

    private String type;
}
