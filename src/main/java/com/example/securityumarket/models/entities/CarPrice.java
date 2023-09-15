package com.example.securityumarket.models.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "car_prices")
public class CarPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


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


    @OneToOne(mappedBy = "price")
    private Car car;
}