package com.example.securityumarket.models.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ParentCategory parentCategory;

    // ... конструктори, геттери, сеттери та інші методи ...
}
