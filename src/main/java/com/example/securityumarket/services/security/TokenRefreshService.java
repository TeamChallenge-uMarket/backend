package com.example.securityumarket.services.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.models.authentication.AuthenticationResponse;
import com.example.securityumarket.models.entities.Users;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TokenRefreshService {

    private final JwtService jwtService;
    private final UsersDAO usersDAO;

    @Autowired
    public TokenRefreshService(JwtService jwtService, UsersDAO usersDAO) {
        this.jwtService = jwtService;
        this.usersDAO = usersDAO;
    }

    public AuthenticationResponse refreshTokens(String refreshToken) {
        try {
            String username = jwtService.extractUsername(refreshToken);
            Users users = usersDAO.findAppUserByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found by refresh token"));

            if (users.getRefreshToken().equals(refreshToken)) {
                String newAccessToken = jwtService.generateToken(users);
                String newRefreshToken = jwtService.generateRefreshToken(users);
                users.setRefreshToken(newRefreshToken);
                usersDAO.save(users);

                return AuthenticationResponse.builder()
                        .token(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .build();
            } else {
                throw new IllegalArgumentException("Invalid refresh token");
            }
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Refresh token has expired", e.getClaims().getExpiration().toInstant());
        }
    }
}
