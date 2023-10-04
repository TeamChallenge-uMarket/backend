package com.example.securityumarket.controllers.main_page;

import com.example.securityumarket.models.DTO.main_page.request.RequestAddCarDTO;
import com.example.securityumarket.models.DTO.main_page.request.RequestCarSearchDTO;
import com.example.securityumarket.models.DTO.main_page.response.*;
import com.example.securityumarket.services.page_service.AddCarService;
import com.example.securityumarket.services.page_service.MainPageService;
import com.example.securityumarket.services.page_service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
@Tag(name = "Main page", description = "main page endpoints")
public class MainPageController {

    private final MainPageService mainPageService;

    private final StorageService storageService;

    private final AddCarService addCarService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "redirect:/api/v1/authorization/login";
    }

    @GetMapping("/add-car")
    public String getAddCarPage() {
        return "add-car";
    }

    @PostMapping("/add-car")
    public ResponseEntity<String> getAddCarPage(
            @RequestPart("requestAddCarDTO") RequestAddCarDTO requestAddCarDTO,
            @RequestPart("multipartFiles") MultipartFile[] multipartFiles) {
        return addCarService.addCar(requestAddCarDTO, multipartFiles);
    }

    @GetMapping("/newCars/{page}/{limit}")
    public ResponseEntity<List<ResponseCarDTO>> getNewCars(
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.getNewCars(page, limit);
    }

    @GetMapping("/popularCars/{page}/{limit}")
    public ResponseEntity<List<ResponseCarDTO>> getPopularsCars(
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.getPopularCars(page, limit);
    }

    @GetMapping("/recentlyViewed/{page}/{limit}")
    public ResponseEntity<List<ResponseCarDTO>> getRecentlyViewedCars(
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.getRecentlyViewedCars(page, limit);
    }

    @GetMapping("/cars/{page}/{limit}")
    public ResponseEntity<List<ResponseCarDTO>> findCars(
            @RequestBody RequestCarSearchDTO requestSearch,
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.searchCarsByRequest(requestSearch, page, limit);
    }

    @GetMapping("/favoriteCars/{page}/{limit}")
    public ResponseEntity<List<ResponseCarDTO>> getFavorites(
            @PathVariable int limit,
            @PathVariable int page) {
        return mainPageService.getFavoriteCars(page, limit);
    }

    @Operation(
            summary = "Retrieve a types",
            description = "Get a type of vehicle. The response is List of Types object with id, description and status.")
    @GetMapping("/type")
    public ResponseEntity<List<ResponseTypeDTO>> getTypeTransport() {
        return mainPageService.getTypeTransport();
    }

    @GetMapping("/brand")
    public ResponseEntity<List<ResponseBrandDTO>> getBrandTransport() {
        return mainPageService.getBrandTransport();
    }

    @GetMapping("/model/{brandId}")
    public ResponseEntity<List<ResponseModelDTO>> getModelTransport(
            @PathVariable long brandId) {
        return mainPageService.getModelTransport(brandId);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<ResponseCitiesDTO>> getCities() {
        return mainPageService.getCities();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) { //TODO TEST METHOD
        return new ResponseEntity<>(storageService.uploadFileWithPublicRead(file), HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}") //TODO TEST METHOD
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

    @DeleteMapping("/delete/{fileName}") //TODO TEST METHOD
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(storageService.deleteFile(fileName), HttpStatus.OK);
    }

    @GetMapping("/get-photo/{fileName}") //TODO TEST METHOD
    public ResponseEntity<String> getPhotoUrl(@PathVariable String fileName) {
        return new ResponseEntity<>(storageService.getFileUrlFromPublicRead(fileName), HttpStatus.OK);
    }

}
