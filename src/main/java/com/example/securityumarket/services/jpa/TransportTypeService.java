package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportTypeDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.TransportType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransportTypeService {

    private final TransportTypeDAO transportTypeDAO;

    public List<TransportType> findAll() {
        List<TransportType> transportTypes = transportTypeDAO.findAll();
        if (transportTypes.isEmpty()) {
            throw new DataNotFoundException("Any transport types");
        }
        return transportTypes;
    }

    public TransportType findById(long transportTypeId) {
        return transportTypeDAO.findById(transportTypeId)
                .orElseThrow(() -> new DataNotFoundException("Transport type by id"));
    }
}
