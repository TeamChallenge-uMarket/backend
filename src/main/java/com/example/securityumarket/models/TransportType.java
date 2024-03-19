package com.example.securityumarket.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "transport_types")
public class TransportType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "type")
    private String type;


    @OneToMany(mappedBy = "transportType")
    private List<TransportTypeBrand> transportTypeBrands;

    @OneToMany(mappedBy = "transportType")
    private List<BodyType> bodyTypes;

    @OneToMany(mappedBy = "transportType")
    private List<DriveType> driveTypes;
}
