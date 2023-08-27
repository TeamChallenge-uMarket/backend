package com.example.securityumarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //jpa auditing to add when created or updated Entity
public class SecurityUmarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityUmarketApplication.class, args);
    }

}
