package com.example.securityumarket.services.main;

import com.example.securityumarket.dao.FavoriteProductsDAO;
import com.example.securityumarket.dao.ProductDAO;
import com.example.securityumarket.models.entities.FavoriteProducts;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.models.favorite.AddMyFavoriteRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoriteService {

    private final UserService userService;
    private final ProductDAO productDAO;
    private final FavoriteProductsDAO favoriteProductsDAO;

    public ResponseEntity<String> addMyFavoriteProducts(AddMyFavoriteRequest addRequest) {
        List<Long> myFavProductIds = addRequest.getMyFavProductIds();

        if (!myFavProductIds.isEmpty()) {
            myFavProductIds.forEach(favProdId -> favoriteProductsDAO.save(
                    FavoriteProducts.builder()
                            .user(userService.getAuthenticatedUser())
                            .product(productDAO.findProductById(favProdId))
                            .build()
            ));
            return ResponseEntity.status(HttpStatus.CREATED).body("Favorite products added");
        } else {
            return ResponseEntity.ok("There is no selected products to add in my favorite");
        }
    }
}
