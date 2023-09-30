package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.RegionDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegionService {
    private final RegionDAO regionDAO;
}
