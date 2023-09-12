package com.example.securityumarket.models.entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "brand_cars")
public class CarBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "description")
    private String description;


    @OneToMany(mappedBy = "carBrand")
    private List<CarModel> carModels;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private CarType carType;
}

