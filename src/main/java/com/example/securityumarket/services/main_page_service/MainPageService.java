package com.example.securityumarket.services.main_page_service;

import com.example.securityumarket.dao.CarDAO;
import com.example.securityumarket.dao.CarViewDAO;
import com.example.securityumarket.models.DTO.main_page.ResponseCarDTO;
import com.example.securityumarket.models.entities.Car;
import com.example.securityumarket.models.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final CarDAO carDAO;
    private final CommonMainService commonMainService;
    private final CarViewDAO carViewDAO;


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
}
