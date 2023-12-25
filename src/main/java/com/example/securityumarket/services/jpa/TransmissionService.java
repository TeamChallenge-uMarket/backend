package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransmissionDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.Transmission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransmissionService {

    private final TransmissionDAO transmissionDAO;

    public Transmission findById(Long transmissionId) {
        return transmissionDAO.findById(transmissionId)
                .orElseThrow(() -> new DataNotFoundException("Transmission by id"));
    }

    public List<Transmission> findAll() {
        return transmissionDAO.findAll();
    }
}
