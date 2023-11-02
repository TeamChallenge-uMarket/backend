package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.exception.DuplicateDataException;
import com.example.securityumarket.models.DTO.entities.user.UserDetailsDTO;
import com.example.securityumarket.models.DTO.login_page.RegisterRequest;
import com.example.securityumarket.models.entities.UserRole;
import com.example.securityumarket.models.entities.Users;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UsersDAO usersDAO;
    private final CityService cityService;

    @Value("${mail.code.expiration.time}")
    private long codeExpirationTimeMs;


    private String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Users getAuthenticatedUser() {
        String email = getAuthenticatedUserEmail();
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
        String authenticatedEmail = getAuthenticatedUserEmail();
        Optional<Users> usersOptional = usersDAO.findAppUserByEmail(authenticatedEmail);
        if (usersOptional.isEmpty()) {
            throw new RuntimeException("User with email: " + authenticatedEmail + " does not exist");
        }

        Users user = usersOptional.get();

        return ResponseEntity.ok(toDto(user));
    }

    public ResponseEntity<String> updateUserDetails(UserDetailsDTO dto) {
        Users users = fromDto(dto);
        usersDAO.save(users);
        return ResponseEntity.ok("User details updated successfully");
    }

    private Users fromDto(UserDetailsDTO dto) {
        return Users.builder()
            .id(dto.getId())
            .name(dto.getName())
            .email(dto.getEmail())
            .city(cityService.findById(dto.getCityId()))
            .photoUrl(dto.getPhotoUrl())
            .phone(normalizePhoneNumber(dto.getPhone()))
            .build();
    }

    private UserDetailsDTO toDto(Users user) {
        UserDetailsDTO dto = new UserDetailsDTO();
        dto.setId(user.getId());
        dto.setCityId(user.getCity().getId());
        dto.setPhone(dto.getPhone());
        dto.setPhotoUrl(user.getPhotoUrl());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());

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

    @Scheduled(cron = "0 0/30 * * * *")
    @Transactional
    public void deleteInactiveUsers() {
        LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minus(codeExpirationTimeMs, ChronoUnit.MILLIS);
        usersDAO.deleteByActiveFalseAndCreatedDateBefore(thirtyMinutesAgo);
    }
}
