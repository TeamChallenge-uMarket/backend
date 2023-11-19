package com.example.securityumarket.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "permissions")
public class Permission extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "description")
    private String description;


    @OneToMany(mappedBy = "permission")
    private List<UserPermission> userPermissions;

    @OneToMany(mappedBy = "permission")
    private List<RolePermission> rolePermissions;

}
