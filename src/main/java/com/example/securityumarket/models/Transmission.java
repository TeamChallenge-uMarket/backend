package com.example.securityumarket.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transmissions")
public class Transmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "transmission")
    private String transmission;
}
