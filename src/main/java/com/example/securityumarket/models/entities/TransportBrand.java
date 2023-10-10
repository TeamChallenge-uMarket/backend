package com.example.securityumarket.models.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "transport_brands")
public class TransportBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "brand")
    private String brand;


    @OneToMany(mappedBy = "transportBrand")
    private List<TransportTypeBrand> transportTypeBrands;
}

