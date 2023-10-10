package com.example.securityumarket.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "roles")
public class Role extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Enumerated(EnumType.STRING)
    private Roles name;


    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

    @OneToMany(mappedBy = "role")
    private List<RolePermission> rolePermissions;


    public enum Roles {
        USER, ADMIN, MODERATOR, GUEST
    }
}
