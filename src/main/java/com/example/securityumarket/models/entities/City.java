package com.example.securityumarket.models.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "description")
    private String description;


    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
}

