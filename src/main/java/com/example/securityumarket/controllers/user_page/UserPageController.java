package com.example.securityumarket.controllers.user_page;

import com.example.securityumarket.models.DTO.entities.user.UserDetailsDTO;
import com.example.securityumarket.services.jpa.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> updateUserDetails(@RequestBody @Valid UserDetailsDTO userDetailsDTO) {
        return userService.updateUserDetails(userDetailsDTO);
    }
}
