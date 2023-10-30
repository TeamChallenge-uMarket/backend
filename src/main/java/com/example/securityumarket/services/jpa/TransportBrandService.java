package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportBrandDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.Region;
import com.example.securityumarket.models.entities.TransportBrand;
import com.example.securityumarket.models.specifications.TransportBrandSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransportBrandService {
    private final TransportBrandDAO transportBrandDAO;

    public List<TransportBrand> findAll() {
        List<TransportBrand> transportBrands = transportBrandDAO.findAll();
        if (transportBrands.isEmpty()) {
            throw new DataNotFoundException("Any transport brands");
        }
        return transportBrands;
    }

    public List<TransportBrand> findAllByTransportTypeId(Long id) {
        return transportBrandDAO.findAllByTransportTypeId(id)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Transport brands by type"));
    }

    public List<TransportBrand> findAllSpecification(Long transportTypeId) {
        return transportBrandDAO.findAll(TransportBrandSpecifications.hasTransportTypeId(transportTypeId));
    }
}
