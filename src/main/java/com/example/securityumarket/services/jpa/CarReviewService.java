package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CarReviewDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CarReviewService {
    private final CarReviewDAO carReviewDAO;
}
