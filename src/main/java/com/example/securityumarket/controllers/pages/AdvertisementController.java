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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/main/advertisements")
@RequiredArgsConstructor
@Tag(name = "Advertisement", description = "Add transport endpoints")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @Operation(description = "This endpoint allows users to add transport")
    @ApiResponse(responseCode = "200", description = "The vehicle has been added successfully",
            content = @Content(mediaType = "application/json"))
    @PostMapping("")
    public ResponseEntity<String> addTransportCloudinary(
            @Parameter(description = "The file which contains the photo of the vehicle")
            @RequestPart("multipartFiles") MultipartFile[] multipartFiles,
            @Parameter(description = "The details of the transport to be added")
            @RequestPart @Valid RequestAddTransportDTO requestAddTransportDTO) {
        advertisementService.addAdvertisement(requestAddTransportDTO, multipartFiles);
        return ResponseEntity.ok("The ad with your transport has been successfully added.");
    }
}
