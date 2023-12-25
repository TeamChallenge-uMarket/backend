package com.example.securityumarket.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wheel_configurations")
public class WheelConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "wheel_configuration")
    private String wheelConfiguration;
}