package com.example.securityumarket.models.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "role_has_permissions")
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "permissions_id")
    private Permission permission;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // ... конструктори, геттери, сеттери та інші методи ...
}

