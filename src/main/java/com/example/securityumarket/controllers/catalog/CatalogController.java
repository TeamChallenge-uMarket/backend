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
    //can be added to my favorite only registered users
    //TODO need to check (tested)
    @PutMapping("/favoriteAdd/{carId}")
    public ResponseEntity<String> addFavorite(
            @PathVariable long carId) {
        return catalogPageService.addFavorite(carId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResponseSearchDTO>> findByMotorcycleFilter(
            @ModelAttribute RequestSearchDTO requestSearchDTO) {
        return catalogPageService.searchTransports(requestSearchDTO);
    }
}