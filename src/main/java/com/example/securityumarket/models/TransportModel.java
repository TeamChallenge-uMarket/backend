package com.example.securityumarket.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transport_models")
public class TransportModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "model")
    private String model;


    @ManyToOne
    @JoinColumn(name = "type_brand_id")
    private TransportTypeBrand transportTypeBrand;
}
