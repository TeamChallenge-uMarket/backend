package com.example.securityumarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subscription_has_transports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransportSubscription extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transport_id")
    private Transport transport;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

}
