package com.example.securityumarket.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "models")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "model")
    private String model;


    @ManyToOne
    @JoinColumn(name = "brand_id")
    private CarBrand carBrand;
}
