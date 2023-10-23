package com.example.securityumarket.controllers.main_page;

import com.example.securityumarket.models.DTO.main_page.request.RequestAddTransportDTO;
import com.example.securityumarket.services.page_service.AddTransportService;
import com.example.securityumarket.services.page_service.StorageService;
import com.example.securityumarket.services.storage.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.ranges.RangeException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/main/add-transport")
@RequiredArgsConstructor
@Tag(name = "Add transport", description = "Add transport endpoints")
public class AddTransportController {

    private final AddTransportService addTransportService;


    private final CloudinaryService cloudinaryService;


    @Operation(description = "This endpoint allows users to add transport")
    @ApiResponse(responseCode = "200", description = "The vehicle has been added successfully",
            content = @Content(mediaType = "application/json"))
    @PostMapping("")
    public ResponseEntity<String> addTransportCloudinary(
            @Parameter(description = "The file which contains the photo of the vehicle")
            @RequestPart("multipartFiles") MultipartFile[] multipartFiles,
            @Parameter(description = "The details of the transport to be added")
            @ModelAttribute @Valid RequestAddTransportDTO requestAddTransportDTO) {
        return addTransportService.addCar(requestAddTransportDTO, multipartFiles);
    }

    @Operation(hidden = true)
    @PostMapping("/cloudinary/upload/") //TEST METHOD
    public ResponseEntity<String> uploadFileCloudinary(@RequestParam(value = "file") MultipartFile file) {
        return new ResponseEntity<>(cloudinaryService.uploadFileWithPublicRead(file), HttpStatus.OK);
    }

    @Operation(hidden = true)
    @GetMapping("/cloudinary/download/{fileName}") //TEST METHOD
    public ResponseEntity<ByteArrayResource> downloadFileCloudinary(@PathVariable String fileName) {
       return cloudinaryService.downloadFileCloudinary(fileName);
    }

    @Operation(hidden = true)
    @GetMapping("/cloudinary/get-url/{fileName}") //TEST METHOD
    public ResponseEntity<String> getOriginalUrl(@PathVariable String fileName) {
        return ResponseEntity.ok(cloudinaryService.getOriginalUrl(fileName));
    }

    @Operation(hidden = true)
    @DeleteMapping("/cloudinary/delete/{fileName}") //TEST METHOD
    public ResponseEntity<String> deleteFileCloudinary(@PathVariable String fileName) {
        return cloudinaryService.deleteFile(fileName);
    }
}
