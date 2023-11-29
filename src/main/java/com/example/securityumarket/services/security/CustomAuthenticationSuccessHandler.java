package com.example.securityumarket.services.security;

import com.example.securityumarket.models.authentication.AuthenticationResponse;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.UserService;
import com.example.securityumarket.util.OAuth2UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
            Users user = processOAuth2User(oAuth2User);
            AuthenticationResponse authenticationResponse = generateAuthenticationResponse(user);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(authenticationResponse));
        }
        else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private AuthenticationResponse generateAuthenticationResponse(Users user) {
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);
        userService.save(user);
        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    private Users processOAuth2User(OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = new OAuth2UserInfo(oAuth2User.getAttributes());
        if (userService.existsUsersByEmail(oAuth2UserInfo.getEmail())) {
            return userService.findAppUserByEmail(oAuth2UserInfo.getEmail());
        } else {
            Users user = buildUserFromOAuth2User(oAuth2UserInfo);
            userService.save(user);
            return user;
        }
    }

    private Users buildUserFromOAuth2User(OAuth2UserInfo oAuth2User) {
        return Users.builder()
                .email(oAuth2User.getEmail())
                .name(oAuth2User.getName())
                .phone(oAuth2User.getPhone())
                .password("test")//TODO password can be null
                .photoUrl(oAuth2User.getPicture())
                .active(true)
                .build();
    }
}