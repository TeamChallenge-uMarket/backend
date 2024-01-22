package com.example.securityumarket.services.pages;

import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.exception.UnauthenticatedException;
import com.example.securityumarket.dto.pages.login.OAuth2Request;
import com.example.securityumarket.dto.authentication.AuthenticationRequest;
import com.example.securityumarket.dto.authentication.AuthenticationResponse;
import com.example.securityumarket.models.Users;
import com.example.securityumarket.services.jpa.UserService;
import com.example.securityumarket.services.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginPageService {

    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        Users user = authenticate(authenticationRequest, false);

        log.info("User with email {} logged in successfully.", authenticationRequest.getEmail());
        return getAuthenticationResponse(user);
    }

    @Transactional
    public AuthenticationResponse loginOAuth2(OAuth2Request oAuth2Request) {
        if (!userService.existsUsersByEmail(oAuth2Request.email())) {
            Users user = buildUserByOAuth2Request(oAuth2Request);
            userService.save(user);

            log.info("New user with email {} created successfully.", oAuth2Request.email());
        }

        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email(oAuth2Request.email())
                .password(oAuth2Request.password())
                .build();

        Users user = authenticate(authenticationRequest, true);

        log.info("User with email {} logged in successfully via OAuth2.", oAuth2Request.email());
        return getAuthenticationResponse(user);
    }

    protected Users authenticate(AuthenticationRequest authenticationRequest,
                                 boolean isGoogleAuthorization) {
        Users user = userService.findAppUserByEmail(authenticationRequest.getEmail());

        if (isGoogleAuthorization) {
            if (!user.getEmail().equals(authenticationRequest.getEmail())) {
                throw new UnauthenticatedException("Account is not authenticated. Reset your password");
            }
            if ((user.getGoogleAccountPassword() == null) || !passwordEncoder.matches(user.getGoogleAccountPassword(), authenticationRequest.getPassword())) {
                user.setGoogleAccountPassword(passwordEncoder.encode(authenticationRequest.getPassword()));
                userService.save(user);
            }
        } else {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authenticationRequest.getEmail(),
                                authenticationRequest.getPassword()
                        )
                );
            } catch (Exception e) {
                throw new UnauthenticatedException("Account is not authenticated. Check your email and password");
            }
        }

        if (!user.isActive()) {
            throw new DataNotValidException("Account not activated. Check your email and activate your account");
        }

        return user;
    }


    private AuthenticationResponse getAuthenticationResponse(Users user) {

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);
        userService.save(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Users buildUserByOAuth2Request(OAuth2Request oAuth2Request) {
        return Users.builder()
                .status(Users.Status.OFFLINE)
                .active(true)
                .googleAccountPassword(passwordEncoder.encode(oAuth2Request.password()))
                .name(oAuth2Request.name())
                .photoUrl(oAuth2Request.picture())
                .email(oAuth2Request.email())
                .build();
    }
}