package com.example.securityumarket.services.main;

import com.example.securityumarket.dao.ProductDAO;
import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.models.entities.Product;
import com.example.securityumarket.models.entities.Users;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UsersDAO usersDAO;
    private final ProductDAO productDAO;

    public ResponseEntity<List<Product>> getProductsById(Long id) {
        List<Product> products = productDAO.findAllByUserId(id);
        if (products == null || products.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

    protected Users getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersDAO.findAppUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not founded"));
    }
}
