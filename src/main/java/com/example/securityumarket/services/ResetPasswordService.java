package com.example.securityumarket.services;

import com.example.securityumarket.dao.AppUserDAO;
import com.example.securityumarket.models.PasswordRequest;
import com.example.securityumarket.models.entities.AppUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResetPasswordService {
    
    private final AppUserDAO appUserDAO;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;



    public ResetPasswordService(AppUserDAO appUserDAO, PasswordEncoder passwordEncoder, MailService mailService) {
        this.appUserDAO = appUserDAO;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    public ResponseEntity<String> resetPassword(PasswordRequest passwordRequest) {
        if (!isValidPassword(passwordRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password must be at least 8 characters long and contain at least one letter and one digit");
        }

        if (!passwordRequest.getPassword().equals(passwordRequest.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        }

        Optional<AppUser> optionalAppUser = appUserDAO.findAppUserByEmail(mailService.getUserEmail());
        AppUser appUser = optionalAppUser.orElseThrow(() -> new UsernameNotFoundException("No user with the given email"));

        appUser.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        appUserDAO.save(appUser);


        return ResponseEntity.ok("Password reset successfully");
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }
}
