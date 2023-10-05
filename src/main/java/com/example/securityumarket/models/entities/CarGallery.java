package com.example.securityumarket.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_galleries")
public class CarGallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "image_name")
    private String imageName;

    @Column(name = "is_main")
    private boolean isMain;

    @Column(name = "url", length = 500)
    private String url;


    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}

