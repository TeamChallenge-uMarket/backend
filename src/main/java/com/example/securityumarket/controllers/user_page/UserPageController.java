package com.example.securityumarket.controllers.user_page;

import com.example.securityumarket.models.DTO.entities.user.UserDetailsDTO;
import com.example.securityumarket.models.DTO.entities.user.UserSecurityDetailsDTO;
import com.example.securityumarket.models.DTO.login_page.PasswordRequest;
import com.example.securityumarket.services.jpa.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/user-page")
@RequiredArgsConstructor
public class UserPageController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDetailsDTO> getUserDetails() {
        return userService.getUserDetails();
    }

    @PutMapping
    public ResponseEntity<String> updateUserDetails(@ModelAttribute @Valid UserDetailsDTO userDetailsDTO,  @RequestPart("multipartFile") MultipartFile photo) {
        return userService.updateUserDetails(userDetailsDTO, photo);
    }

    @PutMapping("/security-info")
    public ResponseEntity<String> updateSecurityInformation(@Valid @RequestBody UserSecurityDetailsDTO securityDetailsDTO) {
        return userService.updateUserSecurityDetails(securityDetailsDTO);
    }
}
