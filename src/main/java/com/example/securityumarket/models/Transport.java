package com.example.securityumarket.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
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
    private Integer year;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "vincode")
    private String vincode;

    @Column(name = "description")
    private String description;

    @Column(name = "engine_displacement")
    private Double engineDisplacement;

    @Column(name = "engine_power")
    private Integer enginePower;

    @Column(name = "number_of_doors")
    private Integer numberOfDoors;

    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    @Column(name = "accident_history")
    private Boolean accidentHistory;

    @Column(name = "fuel_consumption_city")
    private Double fuelConsumptionCity;

    @Column(name = "fuel_consumption_highway")
    private Double fuelConsumptionHighway;

    @Column(name = "fuel_consumption_mixed")
    private Double fuelConsumptionMixed;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "bargain")
    private Boolean bargain;

    @Column(name = "trade")
    private Boolean trade;

    @Column(name = "military")
    private Boolean military;

    @Column(name = "installment_payment")
    private Boolean installmentPayment;

    @Column(name = "uncleared")
    private Boolean uncleared;

    @Column(name = "load_capacity")
    private Integer loadCapacity;

    @Column(name = "phone_views")
    @Builder.Default
    private Integer phoneViews = 0;

    @Column(name = "phone")
    private String phone;

    @Column(insertable = false, name = "status", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private Status status;


    public enum Status {
        ACTIVE, PENDING, INACTIVE, DELETED
    }


    @OneToMany(mappedBy = "transport")
    private List<TransportReview> transportReviews;

    @OneToMany(mappedBy = "transport")
    private List<FavoriteTransport> transportFavorites;

    @OneToMany(mappedBy = "transport")
    private List<TransportView> transportViews;

    @OneToMany(mappedBy = "transport")
    private List<TransportGallery> transportGalleries;

    @OneToMany(mappedBy = "transport")
    private List<HiddenAd> hiddenAds;


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
    @JoinColumn(name = "fuel_type")
    private FuelType fuelType;

    @ManyToOne
    @JoinColumn(name = "body_type")
    private BodyType bodyType;

    @ManyToOne
    @JoinColumn(name = "drive_type")
    private DriveType driveType;

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

    @ManyToOne
    @JoinColumn(name = "number_axles")
    private NumberAxles numberAxles;
}
