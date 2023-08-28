package com.example.securityumarket.models.entities;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "orders")
public class Order extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private String phone;
    private String address;
    private String status;

    // ... конструктори, геттери, сеттери та інші методи ...
}

