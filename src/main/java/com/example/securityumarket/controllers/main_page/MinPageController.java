package com.example.securityumarket.controllers.main_page;

import com.example.securityumarket.models.DTO.main_page.ResponseCarDTO;
import com.example.securityumarket.services.main_page_service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
public class MinPageController {

    private final MainPageService mainPageService;

    @GetMapping("/getNewCars/{page}/{limit}")
    public ResponseEntity<List<ResponseCarDTO>> getNewCars(
            @PathVariable String limit,
            @PathVariable String page) {
        return mainPageService.getNewCars(page, limit);
    }

}
