package com.example.securityumarket.models.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private String name;
    private String description;
    private BigDecimal price;
    @Enumerated(EnumType.STRING) // Додайте анотацію Enumerated для вказання, що використовується перерахування
    private Status status = Status.INACTIVE;

    @OneToMany(mappedBy = "product")
    private List<ProductGallery> images;

    @OneToMany(mappedBy = "product")
    private List<ProductReview> productReviews;

    // ... конструктори, геттери, сеттери та інші методи ...

    public enum Status {
        PENDING, ACTIVE, INACTIVE
    }
}

