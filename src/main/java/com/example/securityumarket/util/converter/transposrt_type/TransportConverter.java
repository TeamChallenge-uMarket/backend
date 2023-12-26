package com.example.securityumarket.util.converter.transposrt_type;

import com.example.securityumarket.dto.pages.catalog.response.ResponseSearch;
import com.example.securityumarket.dto.entities.TransportGalleryDTO;
import com.example.securityumarket.dto.transports.TransportDTO;
import com.example.securityumarket.dto.pages.user.response.TransportByStatusResponse;
import com.example.securityumarket.models.Transport;
import com.example.securityumarket.models.TransportGallery;
import com.example.securityumarket.models.Users;
import com.example.securityumarket.services.jpa.FavoriteTransportService;
import com.example.securityumarket.services.jpa.TransportGalleryService;
import com.example.securityumarket.services.jpa.TransportViewService;
import com.example.securityumarket.services.jpa.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TransportConverter {

    private final TransportGalleryService transportGalleryService;

    private final FavoriteTransportService favoriteTransportService;

    private final TransportViewService transportViewService;

    private final UserService userService;


    public <T extends TransportDTO> T convertTransportToTypeDTO(Transport transport, TransportTypeConversionStrategy strategy) {
        T dto = strategy.createDTO(transport);
        return mapCommonProperties(transport, dto);
    }

    public ResponseSearch convertTransportTransportSearchDTO(Transport transport) {
        return ResponseSearch.builder()
                .id(transport.getId())
                .brand(transport.getTransportModel().getTransportTypeBrand().getTransportBrand().getBrand())
                .model(transport.getTransportModel().getModel())
                .price(transport.getPrice())
                .year(transport.getYear())
                .mileage(transport.getMileage())
                .description(transport.getDescription())
                .transmission((transport.getTransmission() != null) ? transport.getTransmission().getTransmission() : null)
                .fuelType((transport.getFuelType() != null) ? transport.getFuelType().getFuelType() : null)
                .engineDisplacement(transport.getEngineDisplacement())
                .city((transport.getCity() != null) ? transport.getCity().getDescription() : null)
                .fileUrl(transportGalleryService.findMainFileByTransport(transport.getId()))
                .isFavorite(isFavoriteTransport(transport))
                .created(transport.getCreated())
                .lastUpdate(transport.getLastUpdate())
                .build();
    }

    public TransportByStatusResponse convertTransportToTransportByStatusResponse(Transport transport) {
        return  TransportByStatusResponse.builder()
                .lastUpdated(transport.getLastUpdate())
                .addedFavoriteCount(favoriteTransportService.countByTransport(transport))
                .openedPhoneCount(null)//TODO openedPhoneCount
                .viewCount(transportViewService.countByTransport(transport))
                .id(transport.getId())
                .brand(transport.getTransportModel().getTransportTypeBrand().getTransportBrand().getBrand())
                .model(transport.getTransportModel().getModel())
                .price(transport.getPrice())
                .year(transport.getYear())
                .mileage(transport.getMileage())
                .description(transport.getDescription())
                .transmission((transport.getTransmission() != null) ? transport.getTransmission().getTransmission() : null)
                .fuelType((transport.getFuelType() != null) ? transport.getFuelType().getFuelType() : null)
                .engineDisplacement(transport.getEngineDisplacement())
                .city((transport.getCity() != null) ? transport.getCity().getDescription() : null)
                .fileUrl(transportGalleryService.findMainFileByTransport(transport.getId()))
                .isFavorite(isFavoriteTransport(transport))
                .created(transport.getCreated())
                .build();
    }
  
    private <T extends TransportDTO> T mapCommonProperties(Transport transport, T dto) {
        dto.setId(transport.getId());
        dto.setBodyType(transport.getBodyType().getBodyType());
        dto.setImportedFrom(transport.getProducingCountry().getCountry());
        dto.setYear(transport.getYear());
        dto.setPrice(transport.getPrice());
        dto.setBargain(transport.getBargain());
        dto.setTrade(transport.getTrade());
        dto.setMilitary(transport.getMilitary());
        dto.setInstallmentPayment(transport.getInstallmentPayment());
        dto.setUncleared(transport.getUncleared());
        dto.setCondition(transport.getTransportCondition().getCondition());
        dto.setAccidentHistory(transport.getAccidentHistory());
        dto.setVincode(transport.getVincode());
        dto.setDescription(transport.getDescription());
        dto.setCreated(transport.getCreated());
        dto.setLastUpdate(transport.getLastUpdate());
        dto.setColor(transport.getTransportColor().getColor());
        dto.setRegion(transport.getCity().getRegion().getDescription());
        dto.setCity(transport.getCity().getDescription());
        dto.setMainPhoto(transportGalleryService.findMainFileByTransport(transport.getId()));
        dto.setUserName(transport.getUser().getName());
        dto.setModel(transport.getTransportModel().getModel());
        dto.setBrand(transport.getTransportModel().getTransportTypeBrand().getTransportBrand().getBrand());
        dto.setGalleries(buildTransportGalleryDTO(transport));
        return dto;
    }

    private List<TransportGalleryDTO> buildTransportGalleryDTO(Transport transport) {
        List<TransportGallery> transportGalleries = transportGalleryService.findAllByTransportId(transport.getId());
        return transportGalleries.stream().map(galleryList -> TransportGalleryDTO.builder()
                .transportGalleryId(galleryList.getId())
                .transportGalleryUrl(galleryList.getUrl())
                .build()).collect(Collectors.toList());
    }

    private boolean isFavoriteTransport(Transport transport) {
        if (userService.isUserAuthenticated()) {
            Users authenticatedUser = userService.getAuthenticatedUser();
           return favoriteTransportService.isFavoriteByUser(authenticatedUser, transport);
        }
        else {
            return false;
        }
    }

    public List<ResponseSearch> convertTransportListToResponseSearchDTO(List<Transport> transports) {
        return transports.stream()
                .map(this::convertTransportTransportSearchDTO)
                .collect(Collectors.toList());
    }
}

