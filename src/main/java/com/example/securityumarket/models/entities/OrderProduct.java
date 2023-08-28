package com.example.securityumarket.models.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name = "order_products")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private BigDecimal price;
    private String comment;

    // ... конструктори, геттери, сеттери та інші методи ...
}

