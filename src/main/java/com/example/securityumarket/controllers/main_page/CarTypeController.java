package com.example.securityumarket.controllers.main_page;

import com.example.securityumarket.models.DTO.main_page.response.ResponseTypeDTO;
import com.example.securityumarket.services.page_service.MainPageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main/transport-types")
@RequiredArgsConstructor
@Tag(name = "Car type on main page", description = "main page endpoints")
public class CarTypeController {
    private final MainPageService mainPageService;
    @GetMapping
    public ResponseEntity<List<ResponseTypeDTO>> getAllTransportTypes() {
        return mainPageService.getTypeTransport();
    }
}
