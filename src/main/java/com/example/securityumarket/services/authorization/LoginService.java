package com.example.securityumarket.services.authorization;

import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.models.authentication.AuthenticationRequest;
import com.example.securityumarket.models.authentication.AuthenticationResponse;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class LoginService {

    private final UsersDAO usersDAO;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        Users users = authenticate(authenticationRequest);
        String jwtToken = jwtService.generateToken(users);
        String refreshToken = jwtService.generateRefreshToken(users);
        users.setRefreshToken(refreshToken);
        usersDAO.save(users);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    protected Users authenticate(AuthenticationRequest authenticationRequest) {

        Users user = usersDAO.findAppUserByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new DataNotFoundException("User with this email"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        if (!user.isActive()) {
            throw new DataNotValidException("Account not activated. Check your email and activate your account");
        }
        return user;
    }
}
