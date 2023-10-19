package com.example.securityumarket.models.DTO.transports.impl;

import com.example.securityumarket.models.DTO.transports.LoadBearingVehicleDTO;
import com.example.securityumarket.models.DTO.transports.TransportDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Data
public class SpecializedVehicleDTO extends LoadBearingVehicleDTO {
}
