package com.example.securityumarket.models.entities;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "regions")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "description")
    private String description;


    @OneToMany(mappedBy = "region")
    private List<City> cities;
}
