package com.example.securityumarket.controllers.pages;

import com.example.securityumarket.dto.filters.response.FilterParametersResponse;
import com.example.securityumarket.dto.pages.catalog.request.RequestFilterParam;
import com.example.securityumarket.dto.pages.catalog.request.RequestSearch;
import com.example.securityumarket.dto.pages.catalog.response.SearchResponse;
import com.example.securityumarket.dto.pages.catalog.response.TransportSearchResponse;
import com.example.securityumarket.services.pages.CatalogPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
@Tag(name = "Catalog page",
        description = "This controller contains the catalog page endpoints, such as: " +
                "add favorite, remove favorite, searchTransports")
public class CatalogController {

    private final CatalogPageService catalogPageService;

    @Operation(description = "This endpoint allows the user to add a vehicle to favorites by its ID")
    @PutMapping("/favorite-add/{transportId}")
    @ApiResponse(responseCode = "200",
            description = "The vehicle has been successfully added to favorites")
    public ResponseEntity<Long> addFavorite(
            @Parameter(description = "The ID of the vehicle to be added to favorites")
            @PathVariable Long transportId) {
        catalogPageService.addFavorite(transportId);
        return ResponseEntity.ok(transportId);
    }

    @Operation(
            description = "This endpoint allows the user to remove a vehicle from favorites by its ID")
    @DeleteMapping("/favorite-remove/{transportId}")
    @ApiResponse(responseCode = "200",
            description = "The vehicle has been successfully deleted from favorites")
    public ResponseEntity<Long> removeFavorite(
            @Parameter(description = "The ID of the vehicle to be removed from favorites")
            @PathVariable Long transportId) {
        catalogPageService.removeFavorite(transportId);
        return ResponseEntity.ok(transportId);
    }

    @Operation(
            description = "This endpoint allows to search for vehicles using various filtering queries")
    @ApiResponse(responseCode = "200",
            description = "list of vehicles retrieved successfully",
            content = @Content(schema = @Schema(implementation = TransportSearchResponse.class)))
    @GetMapping("/search/page/{page}/limit/{limit}/")
    public ResponseEntity<SearchResponse> searchTransports(
            @Parameter(description = "The number of the page to be displayed")
            @PathVariable int page,
            @Parameter(description = "The number of the vehicles to be displayed on one page")
            @PathVariable int limit,
            @ModelAttribute RequestSearch requestSearch) {
        return ResponseEntity.ok(catalogPageService.searchTransports(page, limit, requestSearch));
    }

    @GetMapping("/get-param")
    public ResponseEntity<FilterParametersResponse> getFilterParameters(
            @ModelAttribute RequestFilterParam requestFilterParam) {
        return ResponseEntity.ok(catalogPageService.getFilterParameters(requestFilterParam));
    }
}
