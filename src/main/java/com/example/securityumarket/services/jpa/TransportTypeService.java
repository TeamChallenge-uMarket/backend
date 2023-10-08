package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportTypeDAO;
import com.example.securityumarket.models.entities.TransportType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransportTypeService {

    private final TransportTypeDAO transportTypeDAO;

    public List<TransportType> findAll() {
        return transportTypeDAO.findAll();
    }
}
