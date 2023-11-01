package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportModelDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.TransportModel;
import com.example.securityumarket.models.entities.TransportType;
import com.example.securityumarket.models.specifications.TransportModelSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TransportModelService {

    private final TransportModelDAO transportModelDAO;

    public TransportModel findById(Long modelId) {
       return transportModelDAO.findById(modelId)
                .orElseThrow(() -> new DataNotFoundException("Model"));
    }

    public List<TransportModel> findAllByTransportTypeAndBrand(long brandId, long typeId) {
        return transportModelDAO.findAllByTransportTypeAndBrand(brandId, typeId)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Models by type and brand"));
    }

    public List<TransportModel> findAllByTransportBrand(long brandId) {
        return transportModelDAO.findAllByTransportBrand(brandId)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Models by brand"));
    }

    public List<TransportModel> findAllByTransportTypeAndBrandSpecification(TransportType transportType, List<Long> transportBrands) {
        if (transportBrands == null || transportBrands.isEmpty()) {
            return Collections.emptyList();
        }
        return transportModelDAO.findAll(
                TransportModelSpecifications.hasTransportTypeId(transportType.getId())
                        .and(TransportModelSpecifications.hasTransportBrandId(transportBrands)));
    }
}
