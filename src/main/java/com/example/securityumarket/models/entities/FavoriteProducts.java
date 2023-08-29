package com.example.securityumarket.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite_products")
public class FavoriteProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}


