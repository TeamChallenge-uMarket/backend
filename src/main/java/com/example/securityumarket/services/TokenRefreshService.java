package com.example.securityumarket.services;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.securityumarket.dao.AppUserDAO;
import com.example.securityumarket.models.authentication.AuthenticationResponse;
import com.example.securityumarket.models.entities.AppUser;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenRefreshService {

    private final JwtService jwtService;
    private final AppUserDAO appUserDAO;

    @Autowired
    public TokenRefreshService(JwtService jwtService, AppUserDAO appUserDAO) {
        this.jwtService = jwtService;
        this.appUserDAO = appUserDAO;
    }

    public AuthenticationResponse refreshTokens(String refreshToken) {
        try {
            String username = jwtService.extractUsername(refreshToken);
            AppUser appUser = appUserDAO.findAppUserByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found by refresh token"));

            if (appUser.getRefreshToken().equals(refreshToken)) {
                String newAccessToken = jwtService.generateToken(appUser);
                String newRefreshToken = jwtService.generateRefreshToken(appUser);
                appUser.setRefreshToken(newRefreshToken);
                appUserDAO.save(appUser);

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
