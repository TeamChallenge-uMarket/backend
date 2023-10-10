package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportReviewDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransportReviewService {
    private final TransportReviewDAO transportReviewDAO;
}
