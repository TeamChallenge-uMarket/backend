package com.example.securityumarket.services.page_service;

import com.example.securityumarket.dao.*;
import com.example.securityumarket.models.DTO.main_page.request.RequestCarSearchDTO;
import com.example.securityumarket.models.DTO.main_page.response.*;
import com.example.securityumarket.models.entities.Car;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService { //TODO do check for uncorrected request

    private final CarDAO carDAO;
    private final CommonMainService commonMainService;
    private final CarViewDAO carViewDAO;
    private final CarTypeDAO carTypeDAO;
    private final CarBrandDAO carBrandDAO;
    private final CarModelDAO carModelDAO;
    private final CityDAO cityDAO;
    private final CarFavoriteDAO carFavoriteDAO;
    private final UserService userService;

    //transform car entity list to carDTO list
    private ResponseEntity<List<ResponseCarDTO>> okResponseCarsDTOList(List<Car> newCars) {
        List<ResponseCarDTO> newCarsResponse = commonMainService.convertCarsListToDtoCarsList(newCars);
        return ResponseEntity.ok(newCarsResponse);
    }

    public ResponseEntity<List<ResponseCarDTO>> getNewCars(int page, int limit) {

        List<Car> newCars = carDAO.findNewCars(PageRequest.of(page, limit));
        return okResponseCarsDTOList(newCars);
    }

    public ResponseEntity<List<ResponseCarDTO>> getPopularCars(int page, int limit) {

        List<Car> popularCars = carViewDAO.findPopularCars(PageRequest.of(page, limit));
        return okResponseCarsDTOList(popularCars);
    }

    public ResponseEntity<List<ResponseCarDTO>> getRecentlyViewedCars(int page, int limit) {
        try {
            Users user = userService.getAuthenticatedUser();
            List<Car> viewedCarsByUser = carViewDAO.findViewedCarsByRegisteredUser(user, PageRequest.of(page, limit));
            return okResponseCarsDTOList(viewedCarsByUser);

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }


    public ResponseEntity<List<ResponseCarDTO>> searchCarsByRequest(RequestCarSearchDTO requestSearch, int page, int limit) {
        List<Car> searchedCars = carDAO.findCarsByRequest(requestSearch, PageRequest.of(page, limit));
        return okResponseCarsDTOList(searchedCars);
    }


    public ResponseEntity<List<ResponseTypeDTO>> getTypeTransport() {
        return ResponseEntity.ok(carTypeDAO.findAll().stream().map(carType -> ResponseTypeDTO.builder()
                .typeId(carType.getId())
                .type(carType.getType())
                .build()).collect(Collectors.toList()));
    }

    public ResponseEntity<List<ResponseBrandDTO>> getBrandTransport() {
        return ResponseEntity.ok(carBrandDAO.findAll().stream().map(carBrand -> ResponseBrandDTO.builder()
                .brandId(carBrand.getId())
                .brand(carBrand.getBrand())
                .build()).collect(Collectors.toList()));
    }

    public ResponseEntity<List<ResponseModelDTO>> getModelTransport(long brandId) {
        return ResponseEntity.ok(carModelDAO.findAllByCarBrand(brandId).stream().map(carModel -> ResponseModelDTO.builder()
                .modelId(carModel.getId())
                .brand(carModel.getCarBrand().getBrand())
                .model(carModel.getModel())
                .build()).collect(Collectors.toList()));
    }

    public ResponseEntity<List<ResponseCitiesDTO>> getCities() {
        return ResponseEntity.ok(cityDAO.findAll().stream().map(city -> ResponseCitiesDTO.builder()
                        .cityId(city.getId())
                        .city(city.getDescription())
                        .build())
                .collect(Collectors.toList()));
    }

    public ResponseEntity<List<ResponseCarDTO>> getFavoriteCars(int page, int limit) {
        try {
            Users user = userService.getAuthenticatedUser();
            List<Car> viewedCarsByUser = carFavoriteDAO.findFavorites(user, PageRequest.of(page, limit));
            return okResponseCarsDTOList(viewedCarsByUser);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }
}
