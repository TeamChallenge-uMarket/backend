package com.example.securityumarket.services.main_page_service;

import com.example.securityumarket.models.DTO.main_page.ResponseCarDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainPageService {
    public ResponseEntity<List<ResponseCarDTO>> getNewCars(String page, String limit) {
        return null;
    }
}
