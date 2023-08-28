package com.example.securityumarket.models.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "product_reviews")
public class ProductReview extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private int rating;
    private String comment;

    // ... конструктори, геттери, сеттери та інші методи ...
}

