package com.example.securityumarket.controllers.main_page;

import com.example.securityumarket.models.DTO.main_page.request.RequestTransportSearchDTO;
import com.example.securityumarket.models.DTO.main_page.response.*;
import com.example.securityumarket.services.page_service.MainPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
@Tag(name = "Main page", description = "main page endpoints")
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "redirect:/api/v1/authorization/login";
    }

    @GetMapping("/add-car")
    public String getAddCarPage() {
        return "add-car";
    }

    @GetMapping("/newCars/{page}/{limit}")
    public ResponseEntity<List<ResponseTransportDTO>> getNewCars(
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.getNewTransports(page, limit);
    }

    @GetMapping("/popularCars/{page}/{limit}")
    public ResponseEntity<List<ResponseTransportDTO>> getPopularsCars(
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.getPopularTransports(page, limit);
    }

    @GetMapping("/recentlyViewed/{page}/{limit}")
    public ResponseEntity<List<ResponseTransportDTO>> getRecentlyViewedCars(
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.getRecentlyViewedTransports(page, limit);
    }

    @GetMapping("/cars/{page}/{limit}")
    public ResponseEntity<List<ResponseTransportDTO>> findCars(
            @RequestBody RequestTransportSearchDTO requestSearch,
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.searchTransportByRequest(requestSearch, page, limit);
    }

    @GetMapping("/favoriteCars/{page}/{limit}")
    public ResponseEntity<List<ResponseTransportDTO>> getFavorites(
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.getFavoriteTransport(page, limit);
    }

    @Operation(
            summary = "Retrieve a types",
            description = "Get a type of vehicle. The response is List of Types object with id, description and status.")
    @GetMapping("/types")
    public ResponseEntity<List<ResponseTypeDTO>> getTypeTransport() {
        return mainPageService.getTypeTransport();
    }

    @GetMapping("brands")
    public ResponseEntity<List<ResponseBrandDTO>> getBrandsByTransportType(@RequestParam(required = false) Long transportTypeId) {
        return mainPageService.getBrandsByTransportType(transportTypeId);
    }
    @GetMapping("models")
    public ResponseEntity<List<ResponseModelDTO>> getModelsByBrand(@RequestParam(required = false) Long transportTypeId, @RequestParam Long transportBrandId) {
        return mainPageService.getModelsByTransportBrand(transportTypeId, transportBrandId);
    }

    @GetMapping("/regions")
    public ResponseEntity<List<ResponseRegionDTO>> getRegions() {
        return mainPageService.getRegions();
    }

    @GetMapping("/cities")
    public ResponseEntity<List<ResponseCityDTO>> getCities(@RequestParam Long regionId) {
        return mainPageService.getCities(regionId);
    }
}

//TODO add controller for catalog (add favorite endpoint)
