package com.example.securityumarket.services.main;

import com.example.securityumarket.dao.FavoriteProductsDAO;
import com.example.securityumarket.dao.ProductDAO;
import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.models.entities.FavoriteProducts;
import com.example.securityumarket.models.entities.Product;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.models.favorite.AddMyFavoriteRequest;
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
    private final FavoriteProductsDAO favoriteProductsDAO;

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

    public ResponseEntity<String> addMyFavoriteProducts(AddMyFavoriteRequest addRequest) {
        Users authUser = getAuthenticatedUser();
        List<Long> myFavProductIds = addRequest.getMyFavProductIds();

        if (!myFavProductIds.isEmpty()) {
            myFavProductIds.forEach(favProd -> favoriteProductsDAO.save(
                    FavoriteProducts.builder()
                            .user(authUser)
                            .product(productDAO.findProductById(favProd))
                            .build()
            ));
            return ResponseEntity.ok("Products added to my favorite");
        } else {
            return ResponseEntity.ok("There is no selected products to add in my favorite");
        }
    }
}
