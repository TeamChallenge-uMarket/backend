package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportBrandDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.TransportBrand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransportBrandService {
    private final TransportBrandDAO transportBrandDAO;

    public List<TransportBrand> findAll() {
        return transportBrandDAO.findAll();
    }

    public List<TransportBrand> findAllByTransportTypeId(Long id) {
        return transportBrandDAO.findAllByTransportTypeId(id)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Car brands by type"));
    }
 }
