package com.example.securityumarket.services.security;

import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.models.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersDAO usersDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersDAO.findAppUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        if (users.getPassword()==null) {
            return new User(users.getEmail(), users.getGoogleAccountPassword(), users.getAuthorities());
        }
        return new User(users.getEmail(), users.getPassword(), users.getAuthorities());
    }
}
