package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.NumberAxlesDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.DTO.entities.NumberAxlesDTO;
import com.example.securityumarket.models.entities.NumberAxles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NumberAxlesService {

    private final NumberAxlesDAO numberAxlesDAO;

    public NumberAxles findById(Long numberAxlesId) {
        return numberAxlesDAO.findById(numberAxlesId)
                .orElseThrow(() -> new DataNotFoundException("NumberAxles by id"));
    }

    public List<NumberAxles> findAll() {
        return numberAxlesDAO.findAll();
    }
}
