package com.example.securityumarket.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "fuel_consumption")
public class FuelConsumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "city")
    private double city;

    @Column(name = "highway")
    private double highway;

    @Column(name = "mixed")
    private double mixed;


    @OneToOne(mappedBy = "fuelConsumption")
    private Car car;
}

