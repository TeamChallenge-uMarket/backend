package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.ProducingCountryDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.ProducingCountry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProducingCountryService {

    private final ProducingCountryDAO producingCountryDAO;

    public ProducingCountry findById(Long producingCountryId) {
        return producingCountryDAO.findById(producingCountryId)
                .orElseThrow(() -> new DataNotFoundException("Country by id"));
    }

    public List<ProducingCountry> findAll() {
        return producingCountryDAO.findAll();
    }
}
