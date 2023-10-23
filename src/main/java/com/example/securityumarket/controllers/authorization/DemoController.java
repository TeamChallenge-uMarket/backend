package com.example.securityumarket.controllers.authorization;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public class DemoController { //TEST CLASS
    @GetMapping("")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("hello secure endpoint");
    }
}
