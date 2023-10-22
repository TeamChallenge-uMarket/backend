package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransmissionDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.Transmission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransmissionService {

    private final TransmissionDAO transmissionDAO;

    public Transmission findById(Long transmissionId) {
        return transmissionDAO.findById(transmissionId)
                .orElseThrow(() -> new DataNotFoundException("Transmission by id"));
    }

}
