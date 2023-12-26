package com.example.securityumarket.models;

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
@Table(name = "transport_galleries")
public class TransportGallery {
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
    @JoinColumn(name = "transport_id")
    private Transport transport;
}

