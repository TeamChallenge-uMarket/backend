package com.example.securityumarket.models.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "permissions")
public class Permission extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "permission")
    private List<UserPermission> userPermissions;

    @OneToMany(mappedBy = "permission")
    private List<RolePermission> rolePermissions;

}
