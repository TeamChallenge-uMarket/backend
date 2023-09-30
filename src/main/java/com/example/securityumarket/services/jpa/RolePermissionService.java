package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.RolePermissionDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RolePermissionService {
    private final RolePermissionDAO rolePermissionDAO;
}
