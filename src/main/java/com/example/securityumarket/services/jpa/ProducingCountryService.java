package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.ProducingCountryDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.ProducingCountry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProducingCountryService {

    private final ProducingCountryDAO producingCountryDAO;

    public ProducingCountry findById(Long producingCountryId) {
        return producingCountryDAO.findById(producingCountryId)
                .orElseThrow(() -> new DataNotFoundException("Country by id"));
    }
}
