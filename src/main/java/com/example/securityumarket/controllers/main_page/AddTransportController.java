package com.example.securityumarket.controllers.main_page;

import com.example.securityumarket.models.DTO.main_page.request.RequestAddTransportDTO;
import com.example.securityumarket.services.page_service.AddTransportService;
import com.example.securityumarket.services.page_service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/main/add-transport")
@RequiredArgsConstructor
@Tag(name = "Add transport", description = "Add transport endpoints")
public class AddTransportController {

    private final AddTransportService addTransportService;

    private final StorageService storageService;


    @Operation(
            summary = "Add Transport",
            description = "This endpoint allows you to advertise transport for sale by authorized user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successful", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient Permissions", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<String> getAddCarPage(
            @RequestPart("requestAddTransportDTO") @Valid RequestAddTransportDTO requestAddTransportDTO,
            @RequestPart("multipartFiles") MultipartFile[] multipartFiles) {
        return addTransportService.addCar(requestAddTransportDTO, multipartFiles);
    }

    @Operation(
            summary = "Upload File",
            description = "Test method - This endpoint allows an authorized user to upload a file for your transport to AWS S3 bucket"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successful", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient Permissions", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
    })
    @PostMapping("/upload") //TEST METHOD
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return new ResponseEntity<>(storageService.uploadFileWithPublicRead(file), HttpStatus.OK);
    }

    @Operation(
            summary = "Download file",
            description = "Test method - This endpoint allows an authorized user to download a file for your transport to AWS S3 bucket"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successful", content = @Content(mediaType = "application/octet-stream",
                    schema = @Schema(implementation = ByteArrayResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient Permissions", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("/download/{fileName}") //TEST METHOD
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = storageService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @Operation(
            summary = "Delete File",
            description = "Test method - This endpoint allows an authorized user to delete a file for your transport to AWS S3 bucket"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successful", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient Permissions", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @DeleteMapping("/delete/{fileName}") //TEST METHOD
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(storageService.deleteFile(fileName), HttpStatus.OK);
    }

    @Operation(
            summary = "Get File",
            description = "Test method - This endpoint allows an authorized user to get file by file name from AWS S3 bucket"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successful", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient Permissions", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("/get-photo/{fileName}") //TEST METHOD
    public ResponseEntity<String> getPhotoUrl(@PathVariable String fileName) {
        return new ResponseEntity<>(storageService.getFileUrlFromPublicRead(fileName), HttpStatus.OK);
    }
}
