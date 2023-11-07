package com.example.securityumarket.controllers.pages.user;

import com.example.securityumarket.models.DTO.catalog_page.response.ResponseDefaultTransportParameter;
import com.example.securityumarket.models.DTO.catalog_page.response.ResponseSearchDTO;
import com.example.securityumarket.models.DTO.entities.user.UserDetailsDTO;
import com.example.securityumarket.models.DTO.entities.user.UserSecurityDetailsDTO;
import com.example.securityumarket.models.DTO.main_page.request.RequestAddTransportDTO;
import com.example.securityumarket.models.DTO.transports.TransportDTO;
import com.example.securityumarket.services.jpa.TransportService;
import com.example.securityumarket.services.jpa.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/user-page")
@RequiredArgsConstructor
public class UserPageController {

    private final UserService userService;

    private final TransportService transportService;


    @GetMapping
    public ResponseEntity<UserDetailsDTO> getUserDetails() {
        return userService.getUserDetails();
    }

    @PutMapping
    public ResponseEntity<String> updateUserDetails(
            @ModelAttribute @Valid UserDetailsDTO userDetailsDTO,
            @RequestPart("multipartFile") MultipartFile photo) {
        return userService.updateUserDetails(userDetailsDTO, photo);
    }

    @PutMapping("/security-info")
    public ResponseEntity<String> updateSecurityInformation(
            @Valid @RequestBody UserSecurityDetailsDTO securityDetailsDTO) {
        return userService.updateUserSecurityDetails(securityDetailsDTO);
    }

    @GetMapping("/my-transports/{status}")
    public ResponseEntity<List<ResponseSearchDTO>> getMyTransports(@PathVariable String status) {
        return transportService.getMyTransportsByStatus(status);
    }

    @PutMapping("/my-transports/{transport-id}/update-status/{status}")
    public ResponseEntity<String> updateTransportStatus(
            @PathVariable("transport-id") Long transportId,
            @PathVariable("status") String status) {
        return transportService.updateTransportStatusByTransportIdAndStatus(transportId, status);
    }

    @GetMapping("/my-transports/get-details/{transport-id}")
    public ResponseEntity<? extends TransportDTO> getTransportDetails(
            @PathVariable("transport-id") Long transportId) {
        return transportService.getTransportDetails(transportId);
    }


    @PutMapping("/my-transports/update/{transport-id}")
    public ResponseEntity<String> updateTransport(
            @RequestBody RequestAddTransportDTO requestAddTransportDTO,
            @PathVariable ("transport-id") Long transportId) {
        return transportService.updateTransport(transportId, requestAddTransportDTO);
    }

}
