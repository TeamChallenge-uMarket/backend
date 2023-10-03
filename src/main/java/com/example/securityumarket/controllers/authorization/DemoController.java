package com.example.securityumarket.controllers.authorization;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController { //TODO TEST CLASS
    @GetMapping("")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("hello secure endpoint");
    }
}