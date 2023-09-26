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

    @Column(name = "url")
    private String url;

    @Column(name = "url_small")
    private String urlSmall;


    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}

