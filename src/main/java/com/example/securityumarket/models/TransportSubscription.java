package com.example.securityumarket.models;

import jakarta.persistence.*;
import lombok.*;

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
