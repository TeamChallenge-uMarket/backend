package com.example.securityumarket.controllers.authorization;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/demo-controller")
@Tag(name = "Demo", description = "protected endpoint with authorized user")
public class DemoController { //TEST CLASS
    @Operation(
            summary = "Secure Home Endpoint",
            description = "This endpoint is a secure home endpoint. It provides a simple response 'hello secure endpoint' as a way to check the security configuration of the application. It is accessible only to authenticated users."
    )
    @GetMapping("")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("hello secure endpoint");
    }
}
