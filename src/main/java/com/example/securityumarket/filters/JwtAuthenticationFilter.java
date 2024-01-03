package com.example.securityumarket.filters;

import com.example.securityumarket.exception.UnauthenticatedException;
import com.example.securityumarket.dto.authentication.AuthenticationResponse;
import com.example.securityumarket.services.security.UserDetailsServiceImpl;
import com.example.securityumarket.services.security.JwtService;
import com.example.securityumarket.services.security.TokenRefreshService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final TokenRefreshService tokenRefreshService;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            String jwt;
            String userEmail;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);

            if (userEmail != null && SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    WebAuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(authenticationDetailsSource.buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else if (jwtService.isTokenExpired(jwt)) {
                    AuthenticationResponse refreshedTokens = tokenRefreshService.refreshTokens(jwt);
                    String newAccessToken = refreshedTokens.getToken();

                    if (newAccessToken != null) {
                        response.setHeader("Authorization", "Bearer " + newAccessToken);
                    }
                }
            }
        } catch (ExpiredJwtException e) {
            throw new UnauthenticatedException("The access token has expired");
        }
        filterChain.doFilter(request, response);
    }
}
