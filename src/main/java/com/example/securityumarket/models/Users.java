package com.example.securityumarket.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users extends DateAudit implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "google_account_password")
    private String googleAccountPassword;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(insertable = false, name = "photo_url", nullable = false,
            columnDefinition = "VARCHAR(255) DEFAULT 'https://res.cloudinary.com/de4bysqtm/image/upload/v1697906978/czkhxykmkfn92deqncp5.jpg'")
    private String photoUrl;

    @Column(name = "status", nullable = false)
    @ColumnDefault("'OFFLINE'")
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        ONLINE,
        OFFLINE

    }
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Transport> transports;

    @OneToMany(mappedBy = "user")
    private List<UserPermission> userPermissions;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRole> userRoles;

    @OneToMany(mappedBy = "user")
    private List<TransportReview> transportReviews;

    @OneToMany(mappedBy = "user")
    private List<FavoriteTransport> favoriteTransports;

    @OneToMany(mappedBy = "user")
    private List<TransportView> transportViews;

    @ManyToOne
    @JoinColumn(name = "city")
    private City city;


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (userRoles != null) {
            for (UserRole userRole : userRoles) {
                authorities.add(new SimpleGrantedAuthority(userRole.getRole().getName().toString()));
            }
        }
        return authorities;
    }
}
