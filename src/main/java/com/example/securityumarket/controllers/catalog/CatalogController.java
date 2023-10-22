package com.example.securityumarket.controllers.catalog;

import com.example.securityumarket.models.DTO.catalog_page.request.RequestSearchDTO;
import com.example.securityumarket.models.DTO.catalog_page.response.ResponseSearchDTO;
import com.example.securityumarket.services.page_service.CatalogPageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
@Tag(name = "Catalog page", description = "catalog page endpoints")
public class CatalogController {
    private final CatalogPageService catalogPageService;

    @PutMapping("/favorite-add/{carId}")
    public ResponseEntity<String> addFavorite(
            @PathVariable long carId) {
        return catalogPageService.addFavorite(carId);
    }

    @DeleteMapping("/favorite-remove/{carId}")
    public ResponseEntity<String> removeFavorite(
            @PathVariable long carId) {
        return catalogPageService.removeFavorite(carId);
    }

    @GetMapping("/search/page/{page}/limit/{limit}/")
    public ResponseEntity<List<ResponseSearchDTO>> searchTransports(
            @PathVariable int page,
            @PathVariable int limit,
            @ModelAttribute RequestSearchDTO requestSearchDTO) {
        return catalogPageService.searchTransports(page, limit, requestSearchDTO);
    }
}