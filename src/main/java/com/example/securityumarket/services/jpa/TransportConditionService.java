package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportConditionDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.TransportCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransportConditionService {

    private final TransportConditionDAO transportConditionDAO;

    public TransportCondition findById(Long transportConditionId) {
        return transportConditionDAO.findById(transportConditionId)
                .orElseThrow(() -> new DataNotFoundException("Condition by id"));
    }
}