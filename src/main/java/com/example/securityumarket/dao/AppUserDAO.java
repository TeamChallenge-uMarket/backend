    package com.example.securityumarket.dao;

    import com.example.securityumarket.models.entities.AppUser;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.Optional;

    public interface AppUserDAO extends JpaRepository<AppUser, Long> {
        Optional<AppUser> findAppUserByEmail(String email);
    }
