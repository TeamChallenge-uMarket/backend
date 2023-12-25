package com.example.securityumarket.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transport_conditions")
public class TransportCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "condition")
    private String condition;
}
