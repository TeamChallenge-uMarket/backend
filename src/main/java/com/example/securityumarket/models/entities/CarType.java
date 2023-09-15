package com.example.securityumarket.models.entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "type_cars")
public class CarType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "type")
    private String type;


    @OneToMany(mappedBy = "carType")
    private List<CarBrand> carBrands;
}
