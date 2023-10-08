package com.example.securityumarket.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transport_type_brands")
public class TransportTypeBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "brand_id")
    private TransportBrand transportBrand;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TransportType transportType;
}
