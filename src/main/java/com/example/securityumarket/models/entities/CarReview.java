package com.example.securityumarket.models.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "product_reviews")
public class CarReview extends CreatedAtAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "rating")
    private int rating;

    @Column(name = "comment")
    private String comment;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}
