package com.example.securityumarket.controllers.pages.user;

import com.example.securityumarket.dto.entities.user.UserDetailsDTO;
import com.example.securityumarket.dto.entities.user.UserSecurityDetailsDTO;
import com.example.securityumarket.dto.transports.TransportDTO;
import com.example.securityumarket.dto.pages.user.request.RequestUpdateTransportDetails;
import com.example.securityumarket.dto.pages.user.response.TransportByStatusResponse;
import com.example.securityumarket.services.pages.UserPageService;
import jakarta.validation.Valid;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user-page")
@RequiredArgsConstructor
public class UserPageController {

    private final UserPageService userPageService;

    @GetMapping
    public ResponseEntity<UserDetailsDTO> getUserDetails() {
        return ResponseEntity.ok(userPageService.getUserDetails());
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserDetails(
            @RequestPart(value = "multipartFile", required = false)
            MultipartFile multipartFile,
            @RequestPart(value = "body") @Valid UserDetailsDTO userDetailsDTO) {
        userPageService.updateUserDetails(userDetailsDTO, multipartFile);
        return ResponseEntity.ok("User details updated successfully");
    }

    @DeleteMapping("/delete-photo")
    public ResponseEntity<String> deleteUserPhoto() {
        userPageService.deleteUserPhoto();
        return ResponseEntity.ok("The user photo has been deleted");
    }

    @PutMapping("/security-info")
    public ResponseEntity<String> updateSecurityInformation(
            @Valid @RequestBody UserSecurityDetailsDTO securityDetailsDTO) {
        userPageService.updateSecurityInformation(securityDetailsDTO);
        return ResponseEntity.ok("User password changed successfully");
    }

    @GetMapping("/my-transports/{status}")
    public ResponseEntity<List<TransportByStatusResponse>> getMyTransports(
            @PathVariable String status) {
        return ResponseEntity.ok(userPageService.getMyTransportsByStatus(status));
    }

    @PutMapping("/my-transports/{transport-id}/update-status/{status}")
    public ResponseEntity<String> updateTransportStatus(
            @PathVariable("transport-id") Long transportId,
            @PathVariable("status") String status) {
        userPageService.updateTransportStatus(transportId, status);
        return ResponseEntity.ok("The status of the transport has been successfully updated");
    }

    @GetMapping("/my-transports/get-details/{transport-id}")
    public ResponseEntity<? extends TransportDTO> getTransportDetails(
            @PathVariable("transport-id") Long transportId) {
        return userPageService.getTransportDetails(transportId);
    }

    @PutMapping("/my-transports/update-details/{transport-id}")
    public ResponseEntity<String> updateTransportDetails(
            @PathVariable("transport-id") Long transportId,

            @RequestPart(value = "multipartFiles", required = false)
            MultipartFile[] multipartFiles,

            @RequestPart(value = "body", required = false)
            @Valid RequestUpdateTransportDetails updateTransportDetails) {
        userPageService.updateTransportDetails(transportId, updateTransportDetails, multipartFiles);
        return ResponseEntity.ok("Transport details updated successfully");
    }

    @DeleteMapping("/my-transports/delete-files/")
    public ResponseEntity<String> deleteGalleryFiles(
            @RequestParam List<Long> galleryId) {
        userPageService.deleteGalleryFiles(galleryId);
        return ResponseEntity.ok("The files have been successfully deleted");
    }
}
