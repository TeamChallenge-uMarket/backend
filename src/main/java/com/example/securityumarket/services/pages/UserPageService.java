package com.example.securityumarket.services.pages;

import com.example.securityumarket.models.DTO.entities.user.UserDetailsDTO;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.TransportGalleryService;
import com.example.securityumarket.services.jpa.TransportService;
import com.example.securityumarket.services.jpa.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class UserPageService {

    private final UserService userService;

    private final TransportService transportService;

    private final TransportGalleryService transportGalleryService;

    public UserDetailsDTO getUserDetails() {
        Users user = userService.getAuthenticatedUser();
        return buildUserDetailsDTOFromUser(user);
    }

    public void updateUserDetails(UserDetailsDTO userDetailsDTO, MultipartFile multipartFile) {

    }

    private UserDetailsDTO buildUserDetailsDTOFromUser(Users user) {
        UserDetailsDTO dto = new UserDetailsDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCityId((user.getCity() != null) ? (user.getCity().getId()) : null);
        dto.setPhone(user.getPhone());
        dto.setPhotoUrl(user.getPhotoUrl());
        return dto;
    }
}
