package com.example.securityumarket.filters;

import com.example.securityumarket.dao.AppUserDAO;
import com.example.securityumarket.models.authentication.AuthenticationResponse;
import com.example.securityumarket.models.RefreshRequest;
import com.example.securityumarket.services.JwtRefreshService;
import com.example.securityumarket.services.LoginService;
import com.example.securityumarket.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public JwtAuthenticationFilter(UserDetailsService userDetailsService, JwtService jwtService, AppUserDAO appUserDAO) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.appUserDAO = appUserDAO;
    }

    private UserDetailsService userDetailsService;
    private JwtService jwtService;
    private AppUserDAO appUserDAO;
    private JwtRefreshService loginService;

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
                } else {

                    AuthenticationResponse refreshedTokens = loginService.refresh(jwt);

                    String newAccessToken = refreshedTokens.getToken();

                    if (newAccessToken != null) {
                        // Оновлюємо токен у заголовку запиту
                        response.setHeader("Authorization", "Bearer " + newAccessToken);

                        // Продовжуємо обробку запиту
                        filterChain.doFilter(request, response);
                    } else {
                        // Якщо оновлення токенів не вдалось, повертаємо помилку або іншу відповідь
                        response.setHeader("Error", "Token refresh failed");
                        return;
                    }
                }
            }
        } catch (IOException | ServletException | UsernameNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ExpiredJwtException e) {
            response.setHeader("Error", "Token is expired");
        }
        filterChain.doFilter(request, response);
    }
}

