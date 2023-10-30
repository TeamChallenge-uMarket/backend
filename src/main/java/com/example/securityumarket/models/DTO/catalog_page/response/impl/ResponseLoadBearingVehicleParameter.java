package com.example.securityumarket.models.DTO.catalog_page.response.impl;

import com.example.securityumarket.models.DTO.catalog_page.response.ResponseDefaultTransportParameter;
import com.example.securityumarket.models.DTO.entities.NumberAxlesDTO;
import com.example.securityumarket.models.DTO.entities.WheelConfigurationDTO;
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
