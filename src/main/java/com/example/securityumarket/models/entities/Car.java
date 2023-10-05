package com.example.securityumarket.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column(name = "fuel_consumption_city")
    private double fuelConsumptionCity;

    @Column(name = "fuel_consumption_highway")
    private double fuelConsumptionHighway;

    @Column(name = "fuel_consumption_mixed")
    private double fuelConsumptionMixed;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "bargain")
    private boolean bargain;

    @Column(name = "trade")
    private boolean trade;

    @Column(name = "military")
    private boolean military;

    @Column(name = "installment_payment")
    private boolean installmentPayment;

    @Column(name = "uncleared")
    private boolean uncleared;


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
