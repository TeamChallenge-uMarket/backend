package com.example.securityumarket.models;

import jakarta.persistence.*;


@Entity
@Table(name = "user_has_permissions")
public class UserPermission extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "permissions_id")
    private Permission permission;
}

