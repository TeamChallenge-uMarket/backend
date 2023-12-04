package com.example.securityumarket.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_search_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "search_history_id")
    private SearchHistory searchHistory;
}
