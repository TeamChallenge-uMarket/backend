package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.exception.UAutoException;
import com.example.securityumarket.models.entities.Users;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
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

}
