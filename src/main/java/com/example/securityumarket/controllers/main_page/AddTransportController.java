package com.example.securityumarket.controllers.main_page;

import com.example.securityumarket.models.DTO.main_page.request.RequestAddTransportDTO;
import com.example.securityumarket.services.page_service.AddTransportService;
import com.example.securityumarket.services.page_service.StorageService;
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


    @PostMapping("")
    public ResponseEntity<String> getAddCarPage(
            @RequestPart("requestAddTransportDTO") @Valid RequestAddTransportDTO requestAddTransportDTO,
            @RequestPart("multipartFiles") MultipartFile[] multipartFiles) {
        return addTransportService.addCar(requestAddTransportDTO, multipartFiles);
    }

    @PostMapping("/upload") //TEST METHOD
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) { //TODO TEST METHOD
        return new ResponseEntity<>(storageService.uploadFileWithPublicRead(file), HttpStatus.OK);
    }

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

    @DeleteMapping("/delete/{fileName}") //TEST METHOD
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(storageService.deleteFile(fileName), HttpStatus.OK);
    }

    @GetMapping("/get-photo/{fileName}") //TEST METHOD
    public ResponseEntity<String> getPhotoUrl(@PathVariable String fileName) {
        return new ResponseEntity<>(storageService.getFileUrlFromPublicRead(fileName), HttpStatus.OK);
    }
}
