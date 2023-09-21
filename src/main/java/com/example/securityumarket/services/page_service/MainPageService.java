package com.example.securityumarket.services.page_service;

import com.example.securityumarket.dao.*;
import com.example.securityumarket.models.DTO.main_page.ResponseCarDTO;
import com.example.securityumarket.models.entities.*;
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

    private final CarDAO carDAO;
    private final CommonMainService commonMainService;
    private final CarViewDAO carViewDAO;
    private final CarTypeDAO carTypeDAO;
    private final CarBrandDAO carBrandDAO;
    private final CarModelDAO carModelDAO;
    private final CityDAO cityDAO;

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
            Users user = commonMainService.getAuthenticatedUser();
            List<Car> viewedCarsByUser = carViewDAO.findViewedCarsByRegisteredUser(user, PageRequest.of(page, limit));
            return okResponseCarsDTOList(viewedCarsByUser);

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }

    //TODO need to test
    public ResponseEntity<List<String>> getTypeTransport() {
        return ResponseEntity.ok(carTypeDAO.findAll().stream().map(CarType::getType).collect(Collectors.toList()));
    }

    public ResponseEntity<List<String>> getBrandTransport() {
        return ResponseEntity.ok(carBrandDAO.findAll().stream().map(CarBrand::getBrand).collect(Collectors.toList()));
    }

    public ResponseEntity<List<String>> getModelTransport(long brandId) {
        return ResponseEntity.ok(carModelDAO.findAllByCarBrand(brandId).stream().map(CarModel::getModel).collect(Collectors.toList()));
    }

    public ResponseEntity<List<String>> getCities() {
        return ResponseEntity.ok(cityDAO.findAll().stream().map(City::getDescription).collect(Collectors.toList()));
    }
}
