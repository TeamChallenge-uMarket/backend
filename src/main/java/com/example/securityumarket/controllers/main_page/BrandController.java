package com.example.securityumarket.controllers.main_page;

import com.example.securityumarket.models.DTO.main_page.response.ResponseBrandDTO;
import com.example.securityumarket.services.page_service.MainPageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main/transport-brand")
@RequiredArgsConstructor
@Tag(name = "Car brand on main page", description = "main page endpoints")
public class BrandController {

    private final MainPageService mainPageService;

    @GetMapping
    public ResponseEntity<List<ResponseBrandDTO>> getBrandsByTransportType(@RequestParam(required = false) Long transportTypeId) {
        return mainPageService.getBrandsByTransportType(transportTypeId);
    }
}
