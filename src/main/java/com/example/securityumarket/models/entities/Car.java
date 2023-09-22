package com.example.securityumarket.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car extends DateAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "year")
    private int year;

    @Column(name = "mileage")
    private int mileage;

    @Column(name = "body_type")
    private String bodyType;

    @Column(name = "vincode")
    private String vincode;

    @Column(name = "description")
    private String description;

    @Column(name = "transmission")
    private String transmission;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "engine_displacement")
    private double engineDisplacement;

    @Column(name = "engine_power")
    private int enginePower;

    @Column(name = "drive_type")
    private String driveType;

    @Column(name = "number_of_doors")
    private int numberOfDoors;

    @Column(name = "number_of_seats")
    private int numberOfSeats;

    @Column(name = "color")
    private String color;

    @Column(name = "imported_from")
    private String importedFrom;

    @Column(name = "accident_history")
    private boolean accidentHistory;

    @Column(name = "condition")
    private String condition;


    @OneToOne()
    @JoinColumn(name = "fuel_consumption_id", referencedColumnName = "id")
    private FuelConsumption fuelConsumption;

    @OneToOne()
    @JoinColumn(name = "price_id", referencedColumnName = "id")
    private CarPrice price;

    @OneToMany(mappedBy = "car")
    private List<CarReview> carReviews;

    @OneToMany(mappedBy = "car")
    private List<CarFavorite> carFavorites;

    @OneToMany(mappedBy = "car")
    private List<CarView> carViews;

    @OneToMany(mappedBy = "car")
    private List<CarGallery> carGalleries;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private CarModel carModel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "city")
    private City city;
}
