package com.example.securityumarket.models;

import jakarta.persistence.*;


@Entity
@Table(name = "role_has_permissions")
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "permissions_id")
    private Permission permission;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}

