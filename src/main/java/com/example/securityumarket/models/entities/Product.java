package com.example.securityumarket.models.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String name;
    private String description;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private Status status = Status.INACTIVE;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user;

    @OneToMany(mappedBy = "product")
    private List<ProductGallery> images;

    @OneToMany(mappedBy = "product")
    private List<FavoriteProducts> favoriteProducts;

    @OneToMany(mappedBy = "product")
    private List<ProductReview> productReviews;


    public enum Status {
        PENDING, ACTIVE, INACTIVE
    }
}

