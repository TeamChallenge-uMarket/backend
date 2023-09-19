package com.example.securityumarket.controllers.main_page;

import com.example.securityumarket.models.DTO.main_page.ResponseCarDTO;
import com.example.securityumarket.services.page_service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
public class MinPageController {

    private final MainPageService mainPageService;

    @GetMapping("/getNewCars/{page}/{limit}")
    public ResponseEntity<List<ResponseCarDTO>> getNewCars(
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.getNewCars(page, limit);
    }

    @GetMapping("/getPopularCars/{page}/{limit}")
    public ResponseEntity<List<ResponseCarDTO>> getPopularsCars(
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.getPopularCars(page, limit);
    }

    //TODO need to test with and without auth
    @GetMapping("/getRecentlyViewed/{page}/{limit}")
    public ResponseEntity<List<ResponseCarDTO>> getRecentlyViewedCars(
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.getRecentlyViewedCars(page, limit);
    }

    @GetMapping("/getType")
    public ResponseEntity<List<String>> getTypeTransport() {
        return mainPageService.getTypeTransport();
    }

    @GetMapping("/getBrand")
    public ResponseEntity<List<String>> getBrandTransport() {
        return mainPageService.getBrandTransport();
    }

    @GetMapping("/getModel")
    public ResponseEntity<List<String>> getModelTransport() {
        return mainPageService.getModelTransport();
    }

    @GetMapping("/getCities")
    public ResponseEntity<List<String>> getCities() {
        return mainPageService.getCities();
    }
}
