package com.example.securityumarket.services.main;

import com.example.securityumarket.dao.FavoriteProductsDAO;
import com.example.securityumarket.dao.ProductDAO;
import com.example.securityumarket.models.DTO.SearchedProductDTO;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.models.favorite.MyFavoriteRequest;
import com.example.securityumarket.services.common.CommonService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    private final CommonService commonService;

    public ResponseEntity<String> addMyFavoriteProducts(MyFavoriteRequest addRequest) {
        Product favoriteProduct = productDAO.findProductById(addRequest.getMyFavProductId());
        Users authenticatedUser = userService.getAuthenticatedUser();

        if (favoriteProductsDAO.findProduct(favoriteProduct) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This product is already in your favorite");
        } else if (favoriteProduct != null) {
            favoriteProductsDAO.save(FavoriteProducts.builder()
                    .product(favoriteProduct)
                    .user(authenticatedUser)
                    .build());
            return ResponseEntity.status(HttpStatus.CREATED).body("Favorite products added");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect product ID to add");
        }
    }

    public ResponseEntity<List<SearchedProductDTO>> getAllMyFavoriteProducts(int page, int limit) {
        PageRequest pageable = PageRequest.of(page, limit);
        return ResponseEntity.ok(commonService.convertProductToSearchedProductDTO(favoriteProductsDAO.getMyFavorite(pageable)));
    }

    public ResponseEntity<String> deleteMyFavorite(MyFavoriteRequest myFavoriteRequest) {
        Product favoriteProduct = productDAO.findProductById(myFavoriteRequest.getMyFavProductId());
        Users authenticatedUser = userService.getAuthenticatedUser();

        if (favoriteProductsDAO.findProduct(favoriteProduct) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect product ID to delete");
        }

        favoriteProductsDAO.deleteMyFavorite(favoriteProduct.getId(), authenticatedUser.getId());
        return ResponseEntity.ok("Favorite product deleted");

    }
}