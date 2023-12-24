package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportTypeBrandDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TransportTypeBrandService {
    private final TransportTypeBrandDAO transportTypeBrandDAO;
}
