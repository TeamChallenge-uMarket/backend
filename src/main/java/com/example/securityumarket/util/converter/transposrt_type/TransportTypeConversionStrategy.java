package com.example.securityumarket.util.converter.transposrt_type;

import com.example.securityumarket.dto.transports.TransportDTO;
import com.example.securityumarket.models.Transport;

public interface TransportTypeConversionStrategy {
    <T extends TransportDTO> T createDTO(Transport transport);
}
