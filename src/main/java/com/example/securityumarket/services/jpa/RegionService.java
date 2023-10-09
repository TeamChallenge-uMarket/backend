package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.RegionDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegionService {

    private final RegionDAO regionDAO;

    public Region findByDescription(String region) {
        return regionDAO.findByDescription(region)
                .orElseThrow(() -> new DataNotFoundException(region));
    }

    public List<Region> findAll() {
        return regionDAO.findAll();
    }
}
