package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportColorDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.TransportColor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransportColorService {

    private final TransportColorDAO transportColorDAO;

    public TransportColor findById(Long transportColorId) {
        return transportColorDAO.findById(transportColorId)
                .orElseThrow(() -> new DataNotFoundException("Color by id"));
    }
}
