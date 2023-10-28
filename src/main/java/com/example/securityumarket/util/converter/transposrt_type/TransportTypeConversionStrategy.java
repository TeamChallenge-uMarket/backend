package com.example.securityumarket.util.converter.transposrt_type;

import com.example.securityumarket.models.DTO.transports.TransportDTO;
import com.example.securityumarket.models.entities.Transport;

public interface TransportTypeConversionStrategy {
    <T extends TransportDTO> T createDTO(Transport transport);
}
