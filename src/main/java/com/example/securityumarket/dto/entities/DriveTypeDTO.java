package com.example.securityumarket.dto.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DriveTypeDTO {

    private long driveTypeId;

    private String driveType;
}
