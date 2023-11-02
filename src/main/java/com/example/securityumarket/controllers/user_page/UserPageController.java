package com.example.securityumarket.controllers.user_page;

import com.example.securityumarket.models.DTO.entities.user.UserDetailsDTO;
import com.example.securityumarket.services.jpa.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> updateUserDetails(@RequestBody UserDetailsDTO userDetailsDTO) {
        return userService.updateUserDetails(userDetailsDTO);
    }
}
