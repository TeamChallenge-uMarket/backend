package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.WheelConfigurationDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.WheelConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WheelConfigurationService {

    private final WheelConfigurationDAO wheelConfigurationDAO;

    public WheelConfiguration findById(Long wheelConfigurationId) {
       return wheelConfigurationDAO.findById(wheelConfigurationId)
                .orElseThrow(() -> new DataNotFoundException("Wheel configuration by id"));
    }

    public List<WheelConfiguration> findAll() {
        return wheelConfigurationDAO.findAll();
    }
}
