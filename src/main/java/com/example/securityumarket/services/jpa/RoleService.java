package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.RoleDAO;
import com.example.securityumarket.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleDAO roleDAO;

    public Role findRoleByName(Role.Roles roles) {
        return roleDAO.findByName(roles);
    }
}
