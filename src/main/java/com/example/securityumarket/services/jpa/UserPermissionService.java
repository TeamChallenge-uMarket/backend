package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.UserPermissionDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserPermissionService {
    private final UserPermissionDAO userPermissionDAO;
}
