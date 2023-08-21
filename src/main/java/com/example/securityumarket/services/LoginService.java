package com.example.securityumarket.services;

import com.example.securityumarket.dao.AppUserDAO;
import com.example.securityumarket.models.RefreshRequest;
import com.example.securityumarket.models.authentication.AuthenticationRequest;
import com.example.securityumarket.models.authentication.AuthenticationResponse;
import com.example.securityumarket.models.entities.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {
    private AppUserDAO appUserDAO;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        AppUser appUser = authenticate(authenticationRequest);

        String jwtToken = jwtService.generateToken(appUser);
        String refreshToken = jwtService.generateRefreshToken(appUser);
        appUser.setRefreshToken(refreshToken);
        appUserDAO.save(appUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    protected AppUser authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        return appUserDAO.findAppUserByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException(
                "no " + "such user and no authenticate"));
    }
}
