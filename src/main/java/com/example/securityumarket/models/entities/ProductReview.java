package com.example.securityumarket.models.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "product_reviews")
public class ProductReview extends CreatedAtAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    // ... конструктори, геттери, сеттери та інші методи ...
}

