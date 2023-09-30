package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.exception.UAutoException;
import com.example.securityumarket.models.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UsersDAO usersDAO;

    private String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Users getAuthenticatedUser() {
        String email = getAuthenticatedUserEmail();
        return usersDAO.findAppUserByEmail(email)
                .orElseThrow(() -> new UAutoException("User not founded"));
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

    public boolean isUserPhoneUnique(String phone) {
        if (existsUsersByPhone(phone)) {
            throw new UAutoException("User with this phone already exists");
        } else return true;
    }

    public void isUserEmailUnique(String email) {
        if (existsUsersByEmail(email)) {
            throw new UAutoException("User with this email already exists");
        }
    }
}
