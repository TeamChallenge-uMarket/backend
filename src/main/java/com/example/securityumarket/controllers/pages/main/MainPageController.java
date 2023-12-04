package com.example.securityumarket.controllers.pages.main;

import com.example.securityumarket.models.DTO.catalog_page.response.ResponseSearchDTO;
import com.example.securityumarket.models.DTO.main_page.response.*;
import com.example.securityumarket.models.DTO.transports.impl.*;
import com.example.securityumarket.services.pages.MainPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
@Tag(name = "Main page", description = "This controller contains main page endpoints")
public class MainPageController {

    private final MainPageService mainPageService;


    @Operation(
            summary = "Retrieve types",
            description = "Get a type of vehicle. The response is List of Transport Types with id, description and status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseTypeDTO.class),
                    examples = @ExampleObject(value = "{\"typeId\": 1, \"type\": \"Boats\"}"))),
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
                    schema = @Schema(implementation = ResponseBrandDTO.class),
                    examples = @ExampleObject(value = "{\"brandId\": 1, \"brand\": \"Volvo\"}"))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("brands")
    public ResponseEntity<List<ResponseBrandDTO>> getBrandsByTransportType(
            @Parameter(description = "ID of transport type")
            @RequestParam(required = false) Long transportTypeId) {
        return mainPageService.getBrandsByTransportType(transportTypeId);
    }

    @Operation(
            summary = "Retrieve models",
            description = "Get a type of vehicle. The response is List of Trans port models with id, brand,and description and status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseModelDTO.class),
                    examples = @ExampleObject(value = "{\"modelId\": 1, \"brand\": \"Toyota\", \"model\": \"Corolla\"}"))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("models")
    public ResponseEntity<List<ResponseModelDTO>> getModelsByBrand(@Parameter(description = "the ID of the transport type")
                                                                       @RequestParam(required = false) Long transportTypeId,
                                                                   @Parameter(description = "the brand ID   of the transport type")
                                                                   @RequestParam Long transportBrandId) {
        return mainPageService.getModelsByTransportBrand(transportTypeId, transportBrandId);
    }

    @Operation(
            summary = "Retrieve regions",
            description = "The response is List of regions with id and region and status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseRegionDTO.class),
                    examples = @ExampleObject(value = "{\"regionId\": 1, \"region\": \"Kharkiv region\"}"))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("/regions")
    public ResponseEntity<List<ResponseRegionDTO>> getRegions() {
        return mainPageService.getRegions();
    }

    @Operation(
            summary = "Retrieve cities by region",
            description = "This endpoint allows the user to get the ist of cities with id, region, city and status by region")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseCityDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("/cities")
    public ResponseEntity<List<ResponseCityDTO>> getCities(
            @Parameter(description = "The ID of the region")
            @RequestParam (required = false) List<Long> regionId) {
        return mainPageService.getCities(regionId);
    }


    @Operation(
            summary = "Get New Transports",
            description = "This endpoint returns a list of new transport from the page."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseSearchDTO.class))),
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
                    schema = @Schema(implementation = ResponseSearchDTO.class))),
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
                    schema = @Schema(implementation = ResponseSearchDTO.class))),
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
                    schema = @Schema(implementation = ResponseSearchDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient Permissions", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("/favorite-transports")
    public ResponseEntity<List<ResponseSearchDTO>> getFavorites() {
        return mainPageService.getFavoriteTransport();
    }
}
