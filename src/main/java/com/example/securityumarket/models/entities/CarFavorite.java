package com.example.securityumarket.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "car_favorites")
public class CarFavorite extends CreatedAtAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}
