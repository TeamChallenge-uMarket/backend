package com.example.securityumarket.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "drive_types")
public class DriveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "drive_type")
    private String driveType;

    @ManyToOne
    @JoinColumn(name = "transport_type")
    private TransportType transportType;
}
