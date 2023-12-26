package com.example.securityumarket.controllers.pages.catalog;

import com.example.securityumarket.dto.pages.catalog.request.RequestFilterParam;
import com.example.securityumarket.dto.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.dto.pages.catalog.response.ResponseDefaultTransportParameter;
import com.example.securityumarket.dto.pages.catalog.response.ResponseSearchDTO;
import com.example.securityumarket.dto.pages.catalog.response.impl.ResponseLoadBearingVehicleParameter;
import com.example.securityumarket.services.pages.CatalogPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
@Tag(name = "Catalog page",
        description = "This controller contains the catalog page endpoints, such as: " +
        "add favorite, remove favorite, searchTransports")
public class CatalogController {

    private final CatalogPageService catalogPageService;

    @Operation(description = "This endpoint allows the user to add a vehicle to favorites by its ID")
    @PutMapping("/favorite-add/{carId}")
    @ApiResponse(responseCode = "200",
            description = "The vehicle has been successfully added to favorites")
    public ResponseEntity<String> addFavorite(
            @Parameter(description = "The ID of the vehicle to be added to favorites")
            @PathVariable long carId) {
        catalogPageService.addFavorite(carId);
        return ResponseEntity.ok("Transport added to favorites");
    }

    @Operation(
            description = "This endpoint allows the user to remove a vehicle from favorites by its ID")
    @DeleteMapping("/favorite-remove/{carId}")
    @ApiResponse(responseCode = "200",
            description = "The vehicle has been successfully deleted from favorites")
    public ResponseEntity<String> removeFavorite(
            @Parameter(description = "The ID of the vehicle to be removed from favorites")
            @PathVariable long carId) {
        catalogPageService.removeFavorite(carId);
        return ResponseEntity.ok("Transport removed from favorites");
    }

    @Operation(
            description = "This endpoint allows to search for vehicles using various filtering queries")
    @ApiResponse(responseCode = "200",
            description = "list of vehicles retrieved successfully",
            content = @Content(schema = @Schema(implementation = ResponseSearchDTO.class)))
    @GetMapping("/search/page/{page}/limit/{limit}/")
    public ResponseEntity<List<ResponseSearchDTO>> searchTransports(
            @Parameter(description = "The number of the page to be displayed")
            @PathVariable int page,
            @Parameter(description = "The number of the vehicles to be displayed on one page")
            @PathVariable int limit,
            @ModelAttribute RequestSearchDTO requestSearchDTO) {
        return ResponseEntity.ok(catalogPageService.searchTransports(page, limit, requestSearchDTO));
    }

    @Operation(
            description = "This endpoint returns a list of values for a vehicle search filter using different filter queries")
    @ApiResponse(responseCode = "200", description = "List of parameters retrieved successfully",
            content = @Content(schema = @Schema(oneOf = {
                    ResponseDefaultTransportParameter.class,
                    ResponseLoadBearingVehicleParameter.class
            })))
    @GetMapping("/get-param")
    public ResponseEntity<? extends ResponseDefaultTransportParameter> getFilterParameters(
            @ModelAttribute RequestFilterParam requestFilterParam) {
        return catalogPageService.getFilterParameters(requestFilterParam);
    }
}
