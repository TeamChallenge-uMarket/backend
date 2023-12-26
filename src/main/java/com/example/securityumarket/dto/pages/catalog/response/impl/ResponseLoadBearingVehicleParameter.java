package com.example.securityumarket.dto.pages.catalog.response.impl;

import com.example.securityumarket.dto.pages.catalog.response.ResponseDefaultTransportParameter;
import com.example.securityumarket.dto.entities.NumberAxlesDTO;
import com.example.securityumarket.dto.entities.WheelConfigurationDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class ResponseLoadBearingVehicleParameter extends ResponseDefaultTransportParameter {

    private List<NumberAxlesDTO> numberAxlesDTOS;

    private List<WheelConfigurationDTO> wheelConfigurationDTOS;
}
