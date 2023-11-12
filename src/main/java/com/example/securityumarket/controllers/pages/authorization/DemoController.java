package com.example.securityumarket.controllers.pages.authorization;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


public class DemoController { //TEST CLASS
    @GetMapping("")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("hello secure endpoint");
    }
}
