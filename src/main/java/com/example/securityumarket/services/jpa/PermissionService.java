package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.PermissionDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PermissionService {
    private final PermissionDAO permissionDAO;
}
