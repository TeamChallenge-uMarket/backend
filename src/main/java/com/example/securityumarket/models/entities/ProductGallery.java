package com.example.securityumarket.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "product_gallery")
public class ProductGallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "is_main")
    private boolean isMain;

    private String url;

    // ... конструктори, геттери, сеттери та інші методи ...
}

