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
    private Long id;

    @Enumerated(EnumType.STRING)
    private Roles name = Roles.USER;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

    @OneToMany(mappedBy = "role")
    private List<RolePermission> rolePermissions;

    // ... конструктори, геттери, сеттери та інші методи ...
    public enum Roles {
        USER, ADMIN, MODERATOR, GUEST
    }
}
