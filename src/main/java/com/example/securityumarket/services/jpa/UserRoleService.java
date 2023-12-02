package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.UserRoleDAO;
import com.example.securityumarket.exception.UnauthenticatedException;
import com.example.securityumarket.models.entities.Role;
import com.example.securityumarket.models.entities.UserRole;
import com.example.securityumarket.models.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserRoleService {

    private final UserRoleDAO userRoleDAO;

    public UserRole save(UserRole userRole) {
        return userRoleDAO.save(userRole);
    }

    public void addUseRole(Users user, Role role) {
        UserRole userRole = buileUserRole(user, role);
        userRoleDAO.save(userRole);
    }

    public UserRole buileUserRole(Users user, Role role) {
        return UserRole.builder()
                .user(user)
                .role(role)
                .build();
    }

    public List<UserRole> findAllByUser(Users user) {
        return userRoleDAO.findAllByUser(user)
                .orElseThrow(() -> new UnauthenticatedException("User role not found."));
    }
}
