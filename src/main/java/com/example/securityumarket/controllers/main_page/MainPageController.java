package com.example.securityumarket.controllers.main_page;

import com.example.securityumarket.models.DTO.main_page.request.RequestCarSearchDTO;
import com.example.securityumarket.models.DTO.main_page.response.*;
import com.example.securityumarket.services.page_service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping("/newCars/{page}/{limit}")
    public ResponseEntity<List<ResponseCarDTO>> getNewCars(
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.getNewCars(page, limit);
    }

    @GetMapping("/popularCars/{page}/{limit}")
    public ResponseEntity<List<ResponseCarDTO>> getPopularsCars(
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.getPopularCars(page, limit);
    }

    //TODO need to test with and without auth
    @GetMapping("/recentlyViewed/{page}/{limit}")
    public ResponseEntity<List<ResponseCarDTO>> getRecentlyViewedCars(
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.getRecentlyViewedCars(page, limit);
    }

    @GetMapping("/cars/{page}/{limit}")
    public ResponseEntity<List<ResponseCarDTO>> findCars(
            @RequestBody RequestCarSearchDTO requestSearch,
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.searchCarsByRequest(requestSearch, page, limit);
    }

    //tested +
    @GetMapping("/type")
    public ResponseEntity<List<ResponseTypeDTO>> getTypeTransport() {
        return mainPageService.getTypeTransport();
    }

    @GetMapping("/brand")
    public ResponseEntity<List<ResponseBrandDTO>> getBrandTransport() {
        return mainPageService.getBrandTransport();
    }

    @GetMapping("/model/{brandId}")
    public ResponseEntity<List<ResponseModelDTO>> getModelTransport(
            @PathVariable long brandId) {
        return mainPageService.getModelTransport(brandId);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<ResponseCitiesDTO>> getCities() {
        return mainPageService.getCities();
    }
}
