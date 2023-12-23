package com.example.securityumarket.models.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_has_subscriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSubscription extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

}
