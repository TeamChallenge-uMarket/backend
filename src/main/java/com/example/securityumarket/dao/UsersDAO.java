    package com.example.securityumarket.dao;

    import com.example.securityumarket.models.entities.Users;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.Optional;

    public interface UsersDAO extends JpaRepository<Users, Long> {

        Optional<Users> findAppUserByEmail(String email);

        boolean existsUsersByEmail(String email);

        boolean existsUsersByPhone(String phone);

    }
