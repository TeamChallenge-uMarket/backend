package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.RoleDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleDAO roleDAO;
}
