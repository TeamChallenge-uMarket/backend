package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.UserRoleDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserRoleService {
    private final UserRoleDAO userRoleDAO;
}
