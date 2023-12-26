package com.example.securityumarket.dto.transports.impl;

import com.example.securityumarket.dto.transports.LoadBearingVehicleDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Data
public class SpecializedVehicleDTO extends LoadBearingVehicleDTO {
}
