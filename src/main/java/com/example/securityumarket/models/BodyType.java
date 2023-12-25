package com.example.securityumarket.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "body_types")
public class BodyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "body_type")
    private String bodyType;

    @ManyToOne
    @JoinColumn(name = "transport_type")
    private TransportType transportType;
}
