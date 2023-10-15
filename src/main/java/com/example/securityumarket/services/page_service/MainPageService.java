package com.example.securityumarket.services.page_service;

import com.example.securityumarket.models.DTO.main_page.request.RequestTransportSearchDTO;
import com.example.securityumarket.models.DTO.main_page.response.*;
import com.example.securityumarket.models.DTO.transports.impl.*;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final TransportService transportService;

    private final FavoriteTransportService favoriteTransportService;

    private final TransportModelService transportModelService;

    private final TransportViewService transportViewService;

    private final TransportTypeService transportTypeService;

    private final TransportBrandService transportBrandService;

    private final CityService cityService;

    private final UserService userService;

    private final RegionService regionService;


    private ResponseEntity<List<ResponseTransportDTO>> okResponseTransportDTOList(List<Transport> newTransports) {
        List<ResponseTransportDTO> newCarsResponse = transportService.convertCarsListToDtoCarsList(newTransports);
        return ResponseEntity.ok(newCarsResponse);
    }

    public ResponseEntity<List<ResponseTransportDTO>> getNewTransports(int page, int limit) {
        List<Transport> newTransports = transportService.findNewCars(PageRequest.of(page, limit));
        return okResponseTransportDTOList(newTransports);
    }

    public ResponseEntity<List<ResponseTransportDTO>> getPopularTransports(int page, int limit) {
        List<Transport> popularTransports = transportViewService.findPopularTransport(PageRequest.of(page, limit));
        return okResponseTransportDTOList(popularTransports);
    }

    public ResponseEntity<List<ResponseTransportDTO>> getRecentlyViewedTransports(int page, int limit) {
            Users user = userService.getAuthenticatedUser();
            List<Transport> viewedCarsByUser = transportViewService.findViewedCarsByRegisteredUser(user, PageRequest.of(page, limit));
            return okResponseTransportDTOList(viewedCarsByUser);
    }

    public ResponseEntity<List<ResponseTransportDTO>> searchTransportByRequest(RequestTransportSearchDTO requestSearch, int page, int limit) {
            List<Transport> searchedTransports = transportService.findTransportByRequest(requestSearch, PageRequest.of(page, limit));
            return okResponseTransportDTOList(searchedTransports);
    }

    public ResponseEntity<List<ResponseTypeDTO>> getTypeTransport() {
        return ResponseEntity.ok(transportTypeService.findAll().stream().map(carType -> ResponseTypeDTO.builder()
                .typeId(carType.getId())
                .type(carType.getType())
                .build()).collect(Collectors.toList()));
    }

    public ResponseEntity<List<ResponseBrandDTO>> getBrandTransport() {
        return ResponseEntity.ok(transportBrandService.findAll().stream().map(carBrand -> ResponseBrandDTO.builder()
                .brandId(carBrand.getId())
                .brand(carBrand.getBrand())
                .build()).collect(Collectors.toList()));
    }

    public ResponseEntity<List<ResponseModelDTO>> getModelTransport(long brandId) {
        return ResponseEntity.ok(transportModelService.findAllByTransportBrand(brandId).stream()
                .map(carModel -> ResponseModelDTO.builder()
                        .modelId(carModel.getId())
                        .brand(carModel.getTransportTypeBrand().getTransportBrand().getBrand())
                        .model(carModel.getModel())
                        .build())
                .collect(Collectors.toList()));
    }

    public ResponseEntity<List<ResponseTransportDTO>> getFavoriteTransport(int page, int limit) {
        try {
            Users user = userService.getAuthenticatedUser();
            List<Transport> viewedCarsByUser = favoriteTransportService.findFavorites(user, PageRequest.of(page, limit));
            return okResponseTransportDTOList(viewedCarsByUser);
        } catch (UsernameNotFoundException exception) {
            return ResponseEntity.noContent().build();
        }
    }

    public ResponseEntity<List<ResponseBrandDTO>> getBrandsByTransportType(Long transportTypeId) {
        if (transportTypeId == null) {
            return getBrandTransport();
        } else {
            return ResponseEntity.ok(transportBrandService.findAllByTransportTypeId(transportTypeId).stream()
                    .map(carBrand -> ResponseBrandDTO.builder()
                            .brandId(carBrand.getId())
                            .brand(carBrand.getBrand())
                            .build())
                    .collect(Collectors.toList()));
        }
    }

    public ResponseEntity<List<ResponseModelDTO>> getModelsByTransportBrand(Long transportTypeId, Long transportBrandId) {
        if (transportTypeId != null) {
            return ResponseEntity.ok(transportModelService.findAllByTransportTypeAndBrand(transportBrandId, transportTypeId).stream()
                    .map(carModel -> ResponseModelDTO.builder()
                            .brand(carModel.getTransportTypeBrand().getTransportBrand().getBrand())
                            .modelId(carModel.getId())
                            .model(carModel.getModel())
                            .build())
                    .collect(Collectors.toList()));
        } else {
            return getModelTransport(transportBrandId);
        }
    }

    public ResponseEntity<List<ResponseRegionDTO>> getRegions() {
        return ResponseEntity.ok(regionService.findAll().stream()
                .map(region -> ResponseRegionDTO.builder()
                        .regionId(region.getId())
                        .region(region.getDescription())
                        .build())
                .collect(Collectors.toList()));
    }

    public ResponseEntity<List<ResponseCityDTO>> getCities(Long regionId) {
        return ResponseEntity.ok(cityService.findByRegion(regionId).stream().map(city -> ResponseCityDTO.builder()
                        .cityId(city.getId())
                        .city(city.getDescription())
                        .region(city.getRegion().getDescription())
                        .build())
                .collect(Collectors.toList()));
    }

    public ResponseEntity<List<PassengerCarDTO>> getPopularPassCar(int page, int limit) {
        List<Transport> popularTransports = transportViewService.findPopularTransportByTypeId(PageRequest.of(page, limit), 1);
        List<PassengerCarDTO> passengerCarDTOS = transportService.convertTransportListToPassCarDTOList(popularTransports);
        return ResponseEntity.ok(passengerCarDTOS);
    }

    public ResponseEntity<List<MotorcycleDTO>> getPopularMotorcycle(int page, int limit) {
        List<Transport> popularTransports = transportViewService.findPopularTransportByTypeId(PageRequest.of(page, limit), 2);
        List<MotorcycleDTO> motorcycleDTOS = transportService.convertTransportListToMotorcycleDTOList(popularTransports);
        return ResponseEntity.ok(motorcycleDTOS);
    }

    public ResponseEntity<List<TruckDTO>> getPopularTrucks(int page, int limit) {
        List<Transport> popularTransports = transportViewService.findPopularTransportByTypeId(PageRequest.of(page, limit), 3);
        List<TruckDTO> truckDTOS = transportService.convertTransportListToTruckDTOOList(popularTransports);
        return ResponseEntity.ok(truckDTOS);
    }

    public ResponseEntity<List<SpecializedVehicleDTO>> getPopularSpecializedVehicles(int page, int limit) {
        List<Transport> popularTransports = transportViewService.findPopularTransportByTypeId(PageRequest.of(page, limit), 4);
        List<SpecializedVehicleDTO> specializedVehicleDTOS = transportService.convertTransportListToSpecializedVehicleDTO(popularTransports);
        return ResponseEntity.ok(specializedVehicleDTOS);
    }

    public ResponseEntity<List<AgriculturalDTO>> getPopularAgricultural(int page, int limit) {
        List<Transport> popularTransports = transportViewService.findPopularTransportByTypeId(PageRequest.of(page, limit), 5);
        List<AgriculturalDTO> agriculturalDTOS = transportService.convertTransportListToAgriculturalDTOList(popularTransports);
        return ResponseEntity.ok(agriculturalDTOS);
    }

    public ResponseEntity<List<WaterVehicleDTO>> getPopularWaterVehicles(int page, int limit) {
        List<Transport> popularTransports = transportViewService.findPopularTransportByTypeId(PageRequest.of(page, limit), 6);
        List<WaterVehicleDTO> waterVehicleDTOS = transportService.convertTransportListToWaterVehicleDTOList(popularTransports);
        return ResponseEntity.ok(waterVehicleDTOS);
    }
}
