package com.example.securityumarket.filters;

import com.example.securityumarket.dao.AppUserDAO;
import com.example.securityumarket.models.authentication.AuthenticationResponse;
import com.example.securityumarket.models.RefreshRequest;
import com.example.securityumarket.services.LoginService;
import com.example.securityumarket.services.JwtService;
import com.example.securityumarket.services.TokenRefreshService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AppUserDAO appUserDAO;
    private final TokenRefreshService tokenRefreshService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
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
                if (jwtService.isTokenValid(jwt, userDetails)
                        && !jwt.equals(appUserDAO.findAppUserByEmail(userEmail).get().getRefreshToken())
                ) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else if (jwtService.isTokenExpired(jwt)) {
                    AuthenticationResponse refreshedTokens = tokenRefreshService.refreshTokens(jwt);

                    String newAccessToken = refreshedTokens.getToken();

                    if (newAccessToken != null) {
                        // Update the access token in the response header
                        response.setHeader("Authorization", "Bearer " + newAccessToken);
                    }
                }
            }
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
        filterChain.doFilter(request, response);
    }
}