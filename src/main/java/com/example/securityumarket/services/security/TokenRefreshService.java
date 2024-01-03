package com.example.securityumarket.services.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.exception.UnauthenticatedException;
import com.example.securityumarket.dto.authentication.AuthenticationResponse;
import com.example.securityumarket.models.Users;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenRefreshService {

    private final JwtService jwtService;
    private final UsersDAO usersDAO;


    public AuthenticationResponse refreshTokens(String refreshToken) {
        try {
            String username = jwtService.extractUsername(refreshToken);
            Users users = usersDAO.findAppUserByEmail(username)
                    .orElseThrow(() -> new UnauthenticatedException("User not found by refresh token"));

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
                throw new UnauthenticatedException("Invalid refresh token");
            }
        } catch (ExpiredJwtException e) {
            throw new UnauthenticatedException("Refresh token has expired");
        }
    }
}
