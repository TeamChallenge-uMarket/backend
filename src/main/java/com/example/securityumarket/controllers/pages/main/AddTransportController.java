package com.example.securityumarket.controllers.pages.main;

import com.example.securityumarket.models.DTO.pages.main.request.RequestAddTransportDTO;
import com.example.securityumarket.services.pages.AddTransportService;
import com.example.securityumarket.services.storage.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/main/add-transport")
@RequiredArgsConstructor
@Tag(name = "Add transport", description = "Add transport endpoints")
public class AddTransportController {

    private final AddTransportService addTransportService;

    @Operation(description = "This endpoint allows users to add transport")
    @ApiResponse(responseCode = "200", description = "The vehicle has been added successfully",
            content = @Content(mediaType = "application/json"))
    @PostMapping("")
    public ResponseEntity<String> addTransportCloudinary(
            @Parameter(description = "The file which contains the photo of the vehicle")
            @RequestPart("multipartFiles") MultipartFile[] multipartFiles,
            @Parameter(description = "The details of the transport to be added")
            @ModelAttribute @Valid RequestAddTransportDTO requestAddTransportDTO) {
        addTransportService.addCar(requestAddTransportDTO, multipartFiles);
        return ResponseEntity.ok("The ad with your transport has been successfully added.");
    }
}
