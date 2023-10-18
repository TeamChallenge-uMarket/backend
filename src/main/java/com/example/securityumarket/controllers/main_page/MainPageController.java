package com.example.securityumarket.controllers.main_page;

import com.example.securityumarket.models.DTO.catalog_page.response.ResponseSearchDTO;
import com.example.securityumarket.models.DTO.main_page.request.RequestTransportSearchDTO;
import com.example.securityumarket.models.DTO.main_page.response.*;
import com.example.securityumarket.models.DTO.transports.impl.*;
import com.example.securityumarket.services.page_service.MainPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
@Tag(name = "Main page", description = "main page endpoints")
public class MainPageController {

    private final MainPageService mainPageService;

    @Operation(
            summary = "Get Login Page",
            description = "This endpoint redirects users to the login page."
    )
    @GetMapping("/login")
    public String getLoginPage() {
        return "redirect:/api/v1/authorization/login";
    }

    @Operation(
            summary = "Get Add Car Page",
            description = "This endpoint returns the 'add car' page for the application."
    )
    @GetMapping("/add-car")
    public String getAddCarPage() {
        return "add-car";
    }


    @Operation(
            summary = "Retrieve types",
            description = "Get a type of vehicle. The response is List of Transport Types with id, description and status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("/types")
    public ResponseEntity<List<ResponseTypeDTO>> getTypeTransport() {
        return mainPageService.getTypeTransport();
    }

    @Operation(
            summary = "Retrieve brands",
            description = "Get a type of vehicle. The response is List of Transport Brands with id, description and status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseBrandDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("brands")
    public ResponseEntity<List<ResponseBrandDTO>> getBrandsByTransportType(@RequestParam(required = false) Long transportTypeId) {
        return mainPageService.getBrandsByTransportType(transportTypeId);
    }

    @Operation(
            summary = "Retrieve models",
            description = "Get a type of vehicle. The response is List of Transport models with id, brand,and description and status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseModelDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("models")
    public ResponseEntity<List<ResponseModelDTO>> getModelsByBrand(@RequestParam(required = false) Long transportTypeId, @RequestParam Long transportBrandId) {
        return mainPageService.getModelsByTransportBrand(transportTypeId, transportBrandId);
    }

    @Operation(
            summary = "Retrieve regions",
            description = "The response is List of regions with id and region and status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseRegionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("/regions")
    public ResponseEntity<List<ResponseRegionDTO>> getRegions() {
        return mainPageService.getRegions();
    }

    @Operation(
            summary = "Retrieve cities",
            description = "The response is List of cities with id, region, city and status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseCityDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("/cities")
    public ResponseEntity<List<ResponseCityDTO>> getCities(@RequestParam Long regionId) {
        return mainPageService.getCities(regionId);
    }


    @Operation(
            summary = "Get New Transports",
            description = "This endpoint returns a list of new transport from the page."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseTransportDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
    })
    @GetMapping("/new-transports")
    public ResponseEntity<List<ResponseSearchDTO>> getNewTransports() {
        return mainPageService.getNewTransports();
    }

    @Operation(
            summary = "Get Popular Transports",
            description = "This endpoint returns a list of popular transport from the page."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseTransportDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
    })
    @GetMapping("/popular-transports")
    public ResponseEntity<List<ResponseSearchDTO>> getPopularsTransports() {
        return mainPageService.getPopularTransports();
    }

    @Operation(
            summary = "Get Recently Viewed Transports",
            description = "This endpoint returns a list of recent view transport by authorized user from the page."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseTransportDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient Permissions", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })

    @GetMapping("/recently-viewed")
    public ResponseEntity<List<ResponseSearchDTO>> getRecentlyViewedTransports() {
        return mainPageService.getRecentlyViewedTransports();
    }

    @Operation(
            summary = "Get Favorite Transports",
            description = "This endpoint returns a list of favorite transports by authorized user from the page."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseTransportDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient Permissions", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("/favorite-transports")
    public ResponseEntity<List<ResponseSearchDTO>> getFavorites() {
        return mainPageService.getFavoriteTransport();
    }

    @Operation(
            summary = "Get Popular Passenger Cars.",
            description = "This endpoint returns a list of popular passenger cars from the page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PassengerCarDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("/popular-passcars") //TEST METHOD
    public ResponseEntity<List<PassengerCarDTO>> getPopularPassCar() {
        return mainPageService.getPopularPassCar();
    }

    @Operation(
            summary = "Get Popular Motorcycles.",
            description = "This endpoint returns a list of popular motorcycles from the page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MotorcycleDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("/popular-motorcycles") //TEST METHOD
    public ResponseEntity<List<MotorcycleDTO>> getPopularMotorcycles() {
        return mainPageService.getPopularMotorcycle();
    }

    @Operation(
            summary = "Get Popular Trucks.",
            description = "This endpoint returns a list of popular trucks from the page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TruckDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("/popular-trucks") //TEST METHOD
    public ResponseEntity<List<TruckDTO>> getPopularTrucks() {
        return mainPageService.getPopularTrucks();
    }

    @Operation(
            summary = "Get Popular Specialized Vehicles.",
            description = "This endpoint returns a list of popular specialized vehicles from the page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SpecializedVehicleDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("/popular-specializedvehicles") //TEST METHOD
    public ResponseEntity<List<SpecializedVehicleDTO>> getPopularSpecializedVehicles() {
        return mainPageService.getPopularSpecializedVehicles();
    }

    @Operation(
            summary = "Get Popular Agricultural Vehicles.",
            description = "This endpoint returns a list of popular agricultural vehicles from the page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AgriculturalDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("/popular-agricultural") //TEST METHOD
    public ResponseEntity<List<AgriculturalDTO>> getPopularAgricultural() {
        return mainPageService.getPopularAgricultural();
    }

    @Operation(
            summary = "Get Popular Water Vehicles.",
            description = "This endpoint returns a list of popular water vehicles from the page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = WaterVehicleDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("/popular-watervehicles") //TEST METHOD
    public ResponseEntity<List<WaterVehicleDTO>> getPopularWaterVehicles() {
        return mainPageService.getPopularWaterVehicles();
    }

    @Operation(
            summary = "Get list of transport by search parameters",
            description = "This endpoint returns a list of transport by search parameters from the page."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseTransportDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
    })
    @GetMapping("/search")
    public ResponseEntity<List<ResponseSearchDTO>> findCars(
            @ModelAttribute RequestTransportSearchDTO requestSearch) {
        return mainPageService.searchTransportByRequest(requestSearch);
    }
}
