package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.exception.DuplicateDataException;
import com.example.securityumarket.exception.UnauthenticatedException;
import com.example.securityumarket.models.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UsersDAO usersDAO;


    @Value("${mail.code.expiration.time}")
    private long codeExpirationTimeMs;


    public Users getAuthenticatedUser() {
        if (isUserAuthenticated()) {
            String email = getAuthenticatedUserEmail();
            return findAppUserByEmail(email);
        } else {
            throw new UnauthenticatedException();
        }
    }

    public Users findAppUserByEmail(String email) {
        return usersDAO.findAppUserByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User with email " + email));
    }

    public void save(Users user) {
        usersDAO.save(user);
    }

    public boolean existsUsersByEmail(String email) {
        return usersDAO.existsUsersByEmail(email);
    }

    public void existsUsersByPhone(String phone) {
        usersDAO.findUserByPhone(phone).ifPresent(users -> {
            throw new DuplicateDataException("User with " + phone + " already exists");
        });
    }

    public boolean isUserAuthenticated() {
        return !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
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

    public void setUserStatusOnline(Users user) {
        user.setStatus(Users.Status.ONLINE);
        save(user);
    }

    public void setUserStatusOffline(Users user) {
        Users userByEmail = findAppUserByEmail(user.getEmail());
        userByEmail.setStatus(Users.Status.OFFLINE);
        save(userByEmail);
    }

    public List<Users> findAllByStatus(Users.Status status) {
        return usersDAO.findAllByStatus(status).orElse(Collections.emptyList());
    }

    private String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Optional<Users> findById(Long userId) {
        return usersDAO.findById(userId);
    }
}