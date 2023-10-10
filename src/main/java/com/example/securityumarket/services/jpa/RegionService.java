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

    public void findByDescription(String region) {
        regionDAO.findByDescription(region)
                .orElseThrow(() -> new DataNotFoundException(region));
    }

    public List<Region> findAll() {
        List<Region> regions = regionDAO.findAll();
        if (regions.isEmpty()) {
            throw new DataNotFoundException("Any regions");
        }
        return regions;
    }
}
