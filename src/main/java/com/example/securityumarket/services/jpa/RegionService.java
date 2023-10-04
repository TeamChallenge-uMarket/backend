package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.RegionDAO;
import com.example.securityumarket.exception.UAutoException;
import com.example.securityumarket.models.entities.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RegionService {

    private final RegionDAO regionDAO;

    public Region findByDescription(String region) {
        return regionDAO.findByDescription(region)
                .orElseThrow(() -> new UAutoException("Region not found"));
    }
}
