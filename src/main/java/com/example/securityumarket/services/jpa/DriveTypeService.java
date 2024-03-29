package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.DriveTypeDAO;
import com.example.securityumarket.dao.specifications.DriveTypeSpecifications;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.DriveType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DriveTypeService {

    private final DriveTypeDAO driveTypeDAO;

    public DriveType findById(Long driveTypeId) {
        return driveTypeDAO.findById(driveTypeId)
                .orElseThrow(() -> new DataNotFoundException("Drive type by id"));
    }

    public List<DriveType> findAllByTransportTypesId(Long transportTypeId) {
        return driveTypeDAO.findAll(DriveTypeSpecifications.hasTransportTypeId(transportTypeId));
    }
}
