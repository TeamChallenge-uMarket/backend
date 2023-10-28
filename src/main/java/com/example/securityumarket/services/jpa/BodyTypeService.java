package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.BodyTypeDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.BodyType;
import com.example.securityumarket.models.specifications.BodyTypeSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BodyTypeService {

    private final BodyTypeDAO bodyTypeDAO;

    public BodyType findById(Long id) {
        return bodyTypeDAO.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Body type by id"));
    }

    public List<BodyType> findAllByTransportTypesId(Long transportTypeId) {
        return bodyTypeDAO.findAll(BodyTypeSpecifications.hasTransportTypeId(transportTypeId));
    }
}