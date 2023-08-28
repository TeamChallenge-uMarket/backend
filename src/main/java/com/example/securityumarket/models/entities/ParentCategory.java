package com.example.securityumarket.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "parent_categories")
public class ParentCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    // ... конструктори, геттери, сеттери та інші методи ...
}

