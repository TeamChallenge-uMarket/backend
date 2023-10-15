package com.example.securityumarket.controllers.catalog;

import com.example.securityumarket.services.page_service.CatalogPageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
