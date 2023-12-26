package com.example.securityumarket.models;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "fuel_types")
public class FuelType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "fuel_type")
    private String fuelType;
}
