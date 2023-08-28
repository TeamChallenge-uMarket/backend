package com.example.securityumarket.models.entities;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "user_has_permissions")
public class UserPermission extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "permissions_id")
    private Permission permission;

    // ... конструктори, геттери, сеттери та інші методи ...
}

