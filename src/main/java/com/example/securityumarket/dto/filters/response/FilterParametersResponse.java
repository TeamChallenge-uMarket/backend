package com.example.securityumarket.dto.filters.response;

import com.example.securityumarket.dto.entities.BodyTypeDTO;
import com.example.securityumarket.dto.entities.DriveTypeDTO;
import com.example.securityumarket.dto.entities.FuelTypeDTO;
import com.example.securityumarket.dto.entities.NumberAxlesDTO;
import com.example.securityumarket.dto.entities.ProducingCountryDTO;
import com.example.securityumarket.dto.entities.TransmissionDTO;
import com.example.securityumarket.dto.entities.TransportBrandDTO;
import com.example.securityumarket.dto.entities.TransportColorDTO;
import com.example.securityumarket.dto.entities.TransportConditionDTO;
import com.example.securityumarket.dto.entities.TransportModelDTO;
import com.example.securityumarket.dto.entities.WheelConfigurationDTO;
import lombok.Builder;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

//@RedisHash("FilterParameters")
@Builder
public class FilterParametersResponse implements Serializable {

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

    private List<NumberAxlesDTO> numberAxlesDTOS;

    private List<WheelConfigurationDTO> wheelConfigurationDTOS;

}
