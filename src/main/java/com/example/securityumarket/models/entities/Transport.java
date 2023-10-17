package com.example.securityumarket.models.entities;

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
@Table(name = "transports")
public class Transport extends DateAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "year")
    private int year;

    @Column(name = "mileage")
    private int mileage;

    @Column(name = "vincode")
    private String vincode;

    @Column(name = "description")
    private String description;

    @Column(name = "engine_displacement")
    private double engineDisplacement;

    @Column(name = "engine_power")
    private int enginePower;

    @Column(name = "number_of_doors")
    private int numberOfDoors;

    @Column(name = "number_of_seats")
    private int numberOfSeats;

    @Column(name = "accident_history")
    private boolean accidentHistory;

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

    @Column(name = "load_capacity")
    private Integer loadCapacity;


    @OneToMany(mappedBy = "transport")
    private List<TransportReview> transportReviews;

    @OneToMany(mappedBy = "transport")
    private List<FavoriteTransport> transportFavorites;

    @OneToMany(mappedBy = "transport")
    private List<TransportView> transportViews;

    @OneToMany(mappedBy = "transport")
    private List<TransportGallery> transportGalleries;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private TransportModel transportModel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "city")
    private City city;

    @ManyToOne
    @JoinColumn(name = "body_type")
    private BodyType bodyType;

    @ManyToOne
    @JoinColumn(name = "drive_type")
    private DriveType driveType;

    @ManyToOne
    @JoinColumn(name = "fuel_type")
    private FuelType fuelType;

    @ManyToOne
    @JoinColumn(name = "producing_country")
    private ProducingCountry producingCountry;

    @ManyToOne
    @JoinColumn(name = "transmission")
    private Transmission transmission;

    @ManyToOne
    @JoinColumn(name = "transport_color")
    private TransportColor transportColor;

    @ManyToOne
    @JoinColumn(name = "transport_condition")
    private TransportCondition transportCondition;

    @ManyToOne
    @JoinColumn(name = "wheel_configuration")
    private WheelConfiguration wheelConfiguration;
}
