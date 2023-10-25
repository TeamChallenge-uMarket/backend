package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.exception.DuplicateDataException;
import com.example.securityumarket.models.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UsersDAO usersDAO;

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

    @Scheduled(cron = "0 0/30 * * * *")
    @Transactional
    public void deleteInactiveUsers() {
        LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minus(codeExpirationTimeMs, ChronoUnit.MILLIS);
        usersDAO.deleteByActiveFalseAndCreatedDateBefore(thirtyMinutesAgo);
    }

}
