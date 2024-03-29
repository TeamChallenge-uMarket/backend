package com.example.securityumarket.controllers.pages;

import com.example.securityumarket.dto.pages.main.request.RequestAddTransportDTO;
import com.example.securityumarket.services.pages.AdvertisementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/main/advertisements")
@RequiredArgsConstructor
@Tag(name = "Advertisement", description = "Add transport endpoints")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @Operation(description = "This endpoint allows users to add transport")
    @ApiResponse(responseCode = "200", description = "The vehicle has been added successfully",
            content = @Content(mediaType = "application/json"))
    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> addTransportCloudinary(
            @RequestPart("multipartFiles") MultipartFile[] multipartFiles,
            @RequestPart @Valid RequestAddTransportDTO requestAddTransportDTO) {

        log.info("request mf = {}",requestAddTransportDTO.toString());
        log.info("request images = {}",multipartFiles.length);
        advertisementService.addAdvertisement(requestAddTransportDTO, multipartFiles);
        return ResponseEntity.ok("The ad with your transport has been successfully added.");
    }

    @PostMapping("/test")
    public ResponseEntity<String> addTestPhoto(
            @RequestPart("multipartFiles") MultipartFile[] multipartFiles) {
        advertisementService.addAdvertisement(multipartFiles);
        return ResponseEntity.ok("The ad with your transport has been successfully added.");
    }

}
