package com.example.securityumarket.services.page_service;

import com.example.securityumarket.exception.UAutoException;
import com.example.securityumarket.models.DTO.main_page.request.RequestCarSearchDTO;
import com.example.securityumarket.models.DTO.main_page.response.*;
import com.example.securityumarket.models.entities.Car;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService { //TODO Return ResponseEntity<String>

    private final CarService carService;

    private final CarFavoriteService carFavoriteService;

    private final CarModelService carModelService;

    private final CarViewService carViewService;

    private final CarTypeService carTypeService;

    private final CarBrandService carBrandService;

    private final CityService cityService;

    private final UserService userService;

    private ResponseEntity<List<ResponseCarDTO>> okResponseCarsDTOList(List<Car> newCars) {
        try {
            List<ResponseCarDTO> newCarsResponse = carService.convertCarsListToDtoCarsList(newCars);
            return ResponseEntity.ok(newCarsResponse);
        } catch (UAutoException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
    }

    public ResponseEntity<List<ResponseCarDTO>> getNewCars(int page, int limit) {
        try {
            List<Car> newCars = carService.findNewCars(PageRequest.of(page, limit));
            return okResponseCarsDTOList(newCars);
        } catch (UAutoException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
    }

    public ResponseEntity<List<ResponseCarDTO>> getPopularCars(int page, int limit) {
        try {
            List<Car> popularCars = carViewService.findPopularCars(PageRequest.of(page, limit));
            return okResponseCarsDTOList(popularCars);
        } catch (UAutoException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>() {
            });
        }
    }

    public ResponseEntity<List<ResponseCarDTO>> getRecentlyViewedCars(int page, int limit) {
        try {
            Users user = userService.getAuthenticatedUser();
            List<Car> viewedCarsByUser = carViewService.findViewedCarsByRegisteredUser(user, PageRequest.of(page, limit));
            return okResponseCarsDTOList(viewedCarsByUser);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
    }

    public ResponseEntity<List<ResponseCarDTO>> searchCarsByRequest(RequestCarSearchDTO requestSearch, int page, int limit) {
        try {
            List<Car> searchedCars = carService.findCarsByRequest(requestSearch, PageRequest.of(page, limit));
            return okResponseCarsDTOList(searchedCars);
        } catch (UAutoException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
    }

    public ResponseEntity<List<ResponseTypeDTO>> getTypeTransport() {
        return ResponseEntity.ok(carTypeService.findAll().stream().map(carType -> ResponseTypeDTO.builder()
                .typeId(carType.getId())
                .type(carType.getType())
                .build()).collect(Collectors.toList()));
    }

    public ResponseEntity<List<ResponseBrandDTO>> getBrandTransport() {
        return ResponseEntity.ok(carBrandService.findAll().stream().map(carBrand -> ResponseBrandDTO.builder()
                .brandId(carBrand.getId())
                .brand(carBrand.getBrand())
                .build()).collect(Collectors.toList()));
    }

    public ResponseEntity<List<ResponseModelDTO>> getModelTransport(long brandId) {
        try {
            return ResponseEntity.ok(carModelService.findAllByCarBrand(brandId).stream().map(carModel -> ResponseModelDTO.builder()
                    .modelId(carModel.getId())
                    .brand(carModel.getCarBrand().getBrand())
                    .model(carModel.getModel())
                    .build()).collect(Collectors.toList()));
        } catch (UAutoException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
    }

    public ResponseEntity<List<ResponseCitiesDTO>> getCities() {
        return ResponseEntity.ok(cityService.findAll().stream().map(city -> ResponseCitiesDTO.builder()
                        .cityId(city.getId())
                        .city(city.getDescription())
                        .build())
                .collect(Collectors.toList()));
    }

    public ResponseEntity<List<ResponseCarDTO>> getFavoriteCars(int page, int limit) {
        try {
            Users user = userService.getAuthenticatedUser();
            List<Car> viewedCarsByUser = carFavoriteService.findFavorites(user, PageRequest.of(page, limit));
            return okResponseCarsDTOList(viewedCarsByUser);
        } catch (UsernameNotFoundException exception) {
            return ResponseEntity.noContent().build();
        }
    }
}
