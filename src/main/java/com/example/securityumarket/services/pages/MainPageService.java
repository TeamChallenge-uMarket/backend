package com.example.securityumarket.services.pages;

import com.example.securityumarket.exception.BadRequestException;
import com.example.securityumarket.models.DTO.pages.catalog.response.ResponseSearchDTO;
import com.example.securityumarket.models.DTO.pages.main.response.*;
import com.example.securityumarket.models.DTO.transports.impl.*;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final TransportService transportService;

    private final TransportModelService transportModelService;

    private final TransportViewService transportViewService;

    private final TransportTypeService transportTypeService;

    private final TransportBrandService transportBrandService;

    private final CityService cityService;

    private final UserService userService;

    private final RegionService regionService;

    private List<ResponseSearchDTO> getResponseTransportDTOList(List<Transport> transports) {
        return transportService.convertTransportListToTransportSearchDTO(transports);
    }

    public List<ResponseSearchDTO> getNewTransports() {
        List<Transport> newTransports = transportService.findNewTransports();
        return getResponseTransportDTOList(newTransports);
    }

    public List<ResponseSearchDTO> getPopularTransports() {
        List<Transport> popularTransports = transportService.findPopularTransport();
        return getResponseTransportDTOList(popularTransports);
    }

    public List<ResponseSearchDTO> getRecentlyViewedTransports() {
        Users user = userService.getAuthenticatedUser();
        List<Transport> viewedCarsByUser = transportService.findViewedTransportsByRegisteredUser(user);
        return getResponseTransportDTOList(viewedCarsByUser);
    }

    public List<ResponseSearchDTO> getFavoriteTransport() {
        Users user = userService.getAuthenticatedUser();
        List<Transport> viewedCarsByUser = transportService.findFavoriteTransportsByRegisteredUser(user);
        return getResponseTransportDTOList(viewedCarsByUser);
    }

    public List<ResponseTypeDTO> getTypeTransport() {
        return transportTypeService.findAll().stream().map(carType -> ResponseTypeDTO.builder()
                .typeId(carType.getId())
                .type(carType.getType())
                .build()).collect(Collectors.toList());
    }

    public List<ResponseBrandDTO> getBrandTransport() {
        return transportBrandService.findAll().stream().map(carBrand -> ResponseBrandDTO.builder()
                .brandId(carBrand.getId())
                .brand(carBrand.getBrand())
                .build())
                .collect(Collectors.toList());
    }

    public List<ResponseModelDTO> getModelTransport(long brandId) {
        return transportModelService.findAllByTransportBrand(brandId).stream()
                .map(carModel -> ResponseModelDTO.builder()
                        .modelId(carModel.getId())
                        .brand(carModel.getTransportTypeBrand().getTransportBrand().getBrand())
                        .model(carModel.getModel())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ResponseBrandDTO> getBrandsByTransportType(Long transportTypeId) {
        if (transportTypeId == null) {
            return getBrandTransport();
        } else {
            return transportBrandService.findAllByTransportTypeId(transportTypeId).stream()
                    .map(carBrand -> ResponseBrandDTO.builder()
                            .brandId(carBrand.getId())
                            .brand(carBrand.getBrand())
                            .build())
                    .collect(Collectors.toList());
        }
    }

    public List<ResponseModelDTO> getModelsByTransportBrand(Long transportTypeId, Long transportBrandId) {
        if (transportBrandId == null) {
            throw new BadRequestException("transportBrandId required");
        }
        if (transportTypeId != null) {
            return transportModelService.findAllByTransportTypeAndBrand(transportBrandId, transportTypeId).stream()
                    .map(carModel -> ResponseModelDTO.builder()
                            .brand(carModel.getTransportTypeBrand().getTransportBrand().getBrand())
                            .modelId(carModel.getId())
                            .model(carModel.getModel())
                            .build())
                    .collect(Collectors.toList());
        } else {
            return getModelTransport(transportBrandId);
        }
    }

    public List<ResponseRegionDTO> getRegions() {
        return regionService.findAll().stream()
                .map(region -> ResponseRegionDTO.builder()
                        .regionId(region.getId())
                        .region(region.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ResponseCityDTO> getCities(List<Long> regionId) {
        return cityService.findAllByRegionId(regionId).stream().map(city -> ResponseCityDTO.builder()
                        .cityId(city.getId())
                        .city(city.getDescription())
                        .region(city.getRegion().getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    public ResponseEntity<List<PassengerCarDTO>> getPopularPassCar() {
        List<Transport> popularTransports = transportViewService.findPopularTransportByTypeId( 1);
        List<PassengerCarDTO> passengerCarDTOS = transportService.convertTransportListToPassCarDTOList(popularTransports);
        return ResponseEntity.ok(passengerCarDTOS);
    }

    public ResponseEntity<List<MotorcycleDTO>> getPopularMotorcycle() {
        List<Transport> popularTransports = transportViewService.findPopularTransportByTypeId(2);
        List<MotorcycleDTO> motorcycleDTOS = transportService.convertTransportListToMotorcycleDTOList(popularTransports);
        return ResponseEntity.ok(motorcycleDTOS);
    }

    public ResponseEntity<List<TruckDTO>> getPopularTrucks() {
        List<Transport> popularTransports = transportViewService.findPopularTransportByTypeId(3);
        List<TruckDTO> truckDTOS = transportService.convertTransportListToTruckDTOOList(popularTransports);
        return ResponseEntity.ok(truckDTOS);
    }

    public ResponseEntity<List<SpecializedVehicleDTO>> getPopularSpecializedVehicles() {
        List<Transport> popularTransports = transportViewService.findPopularTransportByTypeId(4);
        List<SpecializedVehicleDTO> specializedVehicleDTOS = transportService.convertTransportListToSpecializedVehicleDTO(popularTransports);
        return ResponseEntity.ok(specializedVehicleDTOS);
    }

    public ResponseEntity<List<AgriculturalDTO>> getPopularAgricultural() {
        List<Transport> popularTransports = transportViewService.findPopularTransportByTypeId(5);
        List<AgriculturalDTO> agriculturalDTOS = transportService.convertTransportListToAgriculturalDTOList(popularTransports);
        return ResponseEntity.ok(agriculturalDTOS);
    }

    public ResponseEntity<List<WaterVehicleDTO>> getPopularWaterVehicles() {
        List<Transport> popularTransports = transportViewService.findPopularTransportByTypeId(6);
        List<WaterVehicleDTO> waterVehicleDTOS = transportService.convertTransportListToWaterVehicleDTOList(popularTransports);
        return ResponseEntity.ok(waterVehicleDTOS);
    }
}
