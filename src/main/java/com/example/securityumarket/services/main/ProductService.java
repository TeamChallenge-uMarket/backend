package com.example.securityumarket.services.main;

import com.example.securityumarket.dao.ProductDAO;
import com.example.securityumarket.models.DTO.ProductDTO;
import com.example.securityumarket.models.entities.Users;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductDAO productDAO;
    private final UserService userService;
    public ResponseEntity<String> addProduct(ProductDTO productDTO) {
        productDAO.save(buildProduct(productDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body("Product added");
    }

    private Product buildProduct(ProductDTO productDTO) {
        Users user = userService.getAuthenticatedUser();
        return Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .status(Product.Status.PENDING)
                .user(user)
                .build();
    }


}
