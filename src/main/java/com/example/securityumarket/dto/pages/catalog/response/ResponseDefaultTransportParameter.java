package com.example.securityumarket.dto.pages.catalog.response;

import com.example.securityumarket.dto.entities.*;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Data
public class ResponseDefaultTransportParameter {

    private List<TransportBrandDTO> transportBrandDTOS;

    private List<TransportModelDTO> transportModelDTOS;

    private List<BodyTypeDTO> bodyTypeDTOS;

    private List<DriveTypeDTO> driveTypeDTOS;

    private List<FuelTypeDTO> fuelTypeDTOS;

    private List<ProducingCountryDTO> producingCountryDTOS;

    private List<TransmissionDTO> transmissionDTOS;

    private List<TransportColorDTO> transportColorDTOS;

    private List<TransportConditionDTO> transportConditionDTOS;
}
