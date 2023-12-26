package com.example.securityumarket.dto.transports;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class LoadBearingVehicleDTO extends MotorizedFourWheeledVehicleDTO {

    protected int loadCapacity;

    protected String numberOfAxles;

    protected String wheelConfiguration;
}
