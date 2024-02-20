package com.example.securityumarket.dto.pages.catalog.response;

import com.example.securityumarket.dto.entities.*;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
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

    private BigDecimal priceFrom;

    private BigDecimal priceTo;

    private Integer yearsFrom;

    private Integer yearsTo;

    private Integer mileageFrom;

    private Integer mileageTo;

    private Integer enginePowerFrom;

    private Integer enginePowerTo;

    private Double engineDisplacementFrom;

    private Double engineDisplacementTo;

    private Integer numberOfDoorsFrom;

    private Integer numberOfDoorsTo;

    private Integer numberOfSeatsFrom;

    private Integer numberOfSeatsTo;

}
