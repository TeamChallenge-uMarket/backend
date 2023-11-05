package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.exception.DuplicateDataException;
import com.example.securityumarket.exception.UnauthenticatedException;
import com.example.securityumarket.models.DTO.entities.user.UserDetailsDTO;
import com.example.securityumarket.models.DTO.entities.user.UserSecurityDetailsDTO;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.security.JwtService;
import com.example.securityumarket.services.storage.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UsersDAO usersDAO;

    private final CityService cityService;

    private final JwtService jwtService;

    private final CloudinaryService cloudinaryService;

    private final PasswordEncoder passwordEncoder;


    @Value("${mail.code.expiration.time}")
    private long codeExpirationTimeMs;


    private String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Users getAuthenticatedUser() {
        String email = getAuthenticatedUserEmail();
        if (email.equals("anonymousUser")) {
            throw new UnauthenticatedException();
        }
        return findAppUserByEmail(email);
    }

    public Users findAppUserByEmail(String email) {
        return usersDAO.findAppUserByEmail(email)
            .orElseThrow(() -> new DataNotFoundException("User with email " + email));
    }

    public Users save(Users users) {
        return usersDAO.save(users);
    }

    public boolean existsUsersByEmail(String email) {
        return usersDAO.existsUsersByEmail(email);
    }

    public boolean existsUsersByPhone(String email) {
        return usersDAO.existsUsersByPhone(email);
    }

    public boolean isUserAuthenticated() {
        String authenticatedUserEmail = getAuthenticatedUserEmail();
        return !authenticatedUserEmail.equals("anonymousUser");
    }

    public void isUserPhoneUnique(String phone) {
        if (existsUsersByPhone(phone)) {
            throw new DuplicateDataException("User with " + phone + " already exists");
        }
    }

    public void isUserEmailUnique(String email) {
        if (existsUsersByEmail(email)) {
            throw new DuplicateDataException("User with " + email + " already exists");
        }
    }

    public ResponseEntity<UserDetailsDTO> getUserDetails() {
        Users user = getAuthenticatedUser();
        return ResponseEntity.ok(buildUserDetailsDTOFromUser(user));
    }

    public ResponseEntity<String> updateUserDetails(UserDetailsDTO dto, MultipartFile photo) {
        Users currentUser = getAuthenticatedUser();
        Users users = buildUserFromUserDetailsDTO(dto);
        users.setId(currentUser.getId());
        users.setPassword(currentUser.getPassword());
        jwtService.generateToken(currentUser);
        String refreshToken = jwtService.generateRefreshToken(currentUser);
        users.setRefreshToken(refreshToken);

        users.setPhotoUrl(uploadUserPhoto(photo));

        usersDAO.save(users);
        return ResponseEntity.ok("User details updated successfully");
    }

    private Users buildUserFromUserDetailsDTO(UserDetailsDTO dto) {
        return Users.builder()
            .name(dto.getName())
            .email(dto.getEmail())
            .city((dto.getCityId() != null) ? (cityService.findById(dto.getCityId())) : null)
            .phone((dto.getPhone() != null) ? normalizePhoneNumber(dto.getPhone()) : null)
            .active(true)
            .build();
    }

    private UserDetailsDTO buildUserDetailsDTOFromUser(Users user) {
        UserDetailsDTO dto = new UserDetailsDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCityId((user.getCity() != null) ? (user.getCity().getId()) : null);
        dto.setPhone(dto.getPhone());
        dto.setPhotoUrl(user.getPhotoUrl());
        return dto;
    }

    private String normalizePhoneNumber(String inputPhoneNumber) {
        String digitsAndParentheses = inputPhoneNumber.replaceAll("[^\\d()]", "");

        String digitsOnly = digitsAndParentheses.replaceAll("[()]", "");

        String normalizedNumber;
        if (digitsOnly.startsWith("38")) {
            normalizedNumber = "+" + digitsOnly;
        } else {
            normalizedNumber = "+38" + digitsOnly;
        }

        return normalizedNumber;
    }

    public ResponseEntity<String> updateUserSecurityDetails(UserSecurityDetailsDTO securityDetailsDTO) {
        Users currentUser = getAuthenticatedUser();
        if (!passwordEncoder.matches(securityDetailsDTO.getOldPassword(), currentUser.getPassword())) {
             throw new DataNotValidException("The old password is incorrect");
        }

        currentUser.setPassword(passwordEncoder.encode(securityDetailsDTO.getPassword()));
        usersDAO.save(currentUser);

        return ResponseEntity.ok("User password changed successfully");
    }

    private String uploadUserPhoto(MultipartFile photo) {
        String fileName = cloudinaryService.uploadFileWithPublicRead(photo);
        return cloudinaryService.getOriginalUrl(fileName);
    }

    @Scheduled(cron = "0 0/30 * * * *")
    @Transactional
    public void deleteInactiveUsers() {
        LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minus(codeExpirationTimeMs, ChronoUnit.MILLIS);
        usersDAO.deleteByActiveFalseAndCreatedDateBefore(thirtyMinutesAgo);
    }
}
