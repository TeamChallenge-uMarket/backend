package com.example.securityumarket.controllers.catalog;

import com.example.securityumarket.models.DTO.catalog.MotorcycleFilterDTO;
import com.example.securityumarket.models.DTO.transports.impl.MotorcycleDTO;
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

    @PutMapping("/favoriteAdd/{carId}")
    public ResponseEntity<String> addFavorite(
            @PathVariable long carId) {
        return catalogPageService.addFavorite(carId);
    }

    @GetMapping("/motorcycles/{page}/{limit}")
    public ResponseEntity<List<MotorcycleDTO>> findByMotorcycleFilter(
            @ModelAttribute MotorcycleFilterDTO motorcycleFilterDTO,
            @PathVariable int limit,
            @PathVariable int page) {
        return catalogPageService.findMotorcycles(motorcycleFilterDTO, page, limit);
    }
}
