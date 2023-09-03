package com.example.securityumarket.services.main;

import com.example.securityumarket.dao.FavoriteProductsDAO;
import com.example.securityumarket.dao.ProductDAO;
import com.example.securityumarket.models.entities.FavoriteProducts;
import com.example.securityumarket.models.entities.Product;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.models.favorite.AddMyFavoriteRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FavoriteService {

    private final UserService userService;
    private final ProductDAO productDAO;
    private final FavoriteProductsDAO favoriteProductsDAO;

    public ResponseEntity<String> addMyFavoriteProducts(AddMyFavoriteRequest addRequest) {
        Product favoriteProduct = productDAO.findProductById(addRequest.getMyFavProductId());
        Users authenticatedUser = userService.getAuthenticatedUser();

        if (favoriteProductsDAO.findProduct(favoriteProduct) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This product is already in your favorite");
        } else if (favoriteProduct != null){
            favoriteProductsDAO.save(FavoriteProducts.builder()
                    .product(favoriteProduct)
                    .user(authenticatedUser)
                    .build());
            return ResponseEntity.status(HttpStatus.CREATED).body("Favorite products added");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect product ID to add");

        }

    }
}
