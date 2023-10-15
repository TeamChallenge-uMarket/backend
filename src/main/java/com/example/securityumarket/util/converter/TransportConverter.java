package com.example.securityumarket.util.converter;

import com.example.securityumarket.models.DTO.transports.TransportDTO;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.services.jpa.TransportGalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TransportConverter {

    private final TransportGalleryService transportGalleryService;

    public <T extends TransportDTO> T convertTransport(Transport transport, TransportTypeConversionStrategy strategy) {
        T dto = strategy.createDTO(transport);
        return mapCommonProperties(transport, dto);
    }

    private <T extends TransportDTO> T mapCommonProperties(Transport transport, T dto) {
        dto.setId(transport.getId());
        dto.setBodyType(transport.getBodyType());
        dto.setImportedFrom(transport.getImportedFrom());
        dto.setYear(transport.getYear());
        dto.setPrice(transport.getPrice());
        dto.setBargain(transport.isBargain());
        dto.setTrade(transport.isTrade());
        dto.setMilitary(transport.isMilitary());
        dto.setInstallmentPayment(transport.isInstallmentPayment());
        dto.setUncleared(transport.isUncleared());
        dto.setCondition(transport.getCondition());
        dto.setAccidentHistory(transport.isAccidentHistory());
        dto.setVincode(transport.getVincode());
        dto.setDescription(transport.getDescription());
        dto.setCreated(transport.getCreated());
        dto.setLastUpdate(transport.getLastUpdate());
        dto.setColor(transport.getColor());
        dto.setRegion(transport.getCity().getRegion().getDescription());
        dto.setCity(transport.getCity().getDescription());
        dto.setMainPhoto(transportGalleryService.findMainFileByTransport(transport.getId()));
        dto.setUserName(transport.getUser().getName());
        dto.setModel(transport.getTransportModel().getModel());
        dto.setBrand(transport.getTransportModel().getTransportTypeBrand().getTransportBrand().getBrand());
        return dto;
    }
}

