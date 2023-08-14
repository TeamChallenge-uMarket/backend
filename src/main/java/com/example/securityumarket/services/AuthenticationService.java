package com.example.securityumarket.services;

import com.example.securityumarket.dao.AppUserDAO;
import com.example.securityumarket.models.*;
import com.example.securityumarket.models.entities.AppUser;
import com.example.securityumarket.models.entities.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthenticationService {
    private PasswordEncoder passwordEncoder;
    private AppUserDAO appUserDAO;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        AppUser appUser = AppUser.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phone(registerRequest.getPhone())
                .country(registerRequest.getCountry())
                .city(registerRequest.getCity())
                .role(Role.USER)
                .build();
        String jwtToken = jwtService.generateToken(appUser);

        String refreshToken = jwtService.generateRefreshToken(appUser);
        appUser.setRefreshToken(refreshToken);

        appUserDAO.save(appUser);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        AppUser appUser = appUserDAO.findAppUserByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException(
                "no " + "such user and no authenticate"));

        String jwtToken = jwtService.generateToken(appUser);
        String refreshToken = jwtService.generateRefreshToken(appUser);
        appUser.setRefreshToken(refreshToken);
        appUserDAO.save(appUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refresh(RefreshRequest refreshRequest) {
        String token = refreshRequest.getRefreshToken();
        String username = jwtService.extractUsername(token);
        AppUser appUser = appUserDAO
                .findAppUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found by refresh token"));
        String newAccessToken = null;
        String newRefreshToken = null;
        if(appUser.getRefreshToken().equals(token)) {
            newAccessToken = jwtService.generateToken(appUser);
            newRefreshToken = jwtService.generateRefreshToken(appUser);
            appUser.setRefreshToken(newRefreshToken);
            appUserDAO.save(appUser);
        }
        return AuthenticationResponse.builder()
                .token(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
