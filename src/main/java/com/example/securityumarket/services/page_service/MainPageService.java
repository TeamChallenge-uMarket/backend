package com.example.securityumarket.services.page_service;

import com.example.securityumarket.models.DTO.main_page.request.RequestTransportSearchDTO;
import com.example.securityumarket.models.DTO.main_page.response.*;
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
public class MainPageService { //TODO Return ResponseEntity<String>

    private final TransportService transportService;

    private final FavoriteTransportService favoriteTransportService;

    private final TransportModelService transportModelService;

    private final TransportViewService transportViewService;

    private final TransportTypeService transportTypeService;

    private final TransportBrandService transportBrandService;

    private final CityService cityService;

    private final UserService userService;


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

    public ResponseEntity<List<ResponseTypeDTO>> getTypeTransport() { //TODO GOOD
        return ResponseEntity.ok(transportTypeService.findAll().stream().map(carType -> ResponseTypeDTO.builder()
                .typeId(carType.getId())
                .type(carType.getType())
                .build()).collect(Collectors.toList()));
    }

    public ResponseEntity<List<ResponseBrandDTO>> getBrandTransport() {//TODO GOOD
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

    public ResponseEntity<List<ResponseCitiesDTO>> getCities() {
        return ResponseEntity.ok(cityService.findAll().stream().map(city -> ResponseCitiesDTO.builder()
                        .cityId(city.getId())
                        .city(city.getDescription())
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
}
