package com.example.securityumarket.dto.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransmissionDTO {

    private long transmissionId;

    private String transmission;
}
