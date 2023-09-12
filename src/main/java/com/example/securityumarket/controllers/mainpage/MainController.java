package com.example.securityumarket.controllers.mainpage;

import com.example.securityumarket.models.DTO.CategoryDTO;
import com.example.securityumarket.models.DTO.ParentCategoryDTO;
import com.example.securityumarket.models.DTO.ProductDTO;
import com.example.securityumarket.models.DTO.SearchedProductDTO;
import com.example.securityumarket.models.favorite.MyFavoriteRequest;
import com.example.securityumarket.services.main.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main")
@AllArgsConstructor
public class MainController {

    ParentCategoryService parentCategoryService;
    CategoryService categoryService;
    ProductService productService;
    UserService userService;
    FavoriteService favoriteService;

    @GetMapping("/add-product")
    public ResponseEntity<String> openAddProduct() {
        return ResponseEntity.ok("Secure endpoint - open add product page");
    }

    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @PostMapping("/add-category")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.addCategory(categoryDTO);
    }

    @PostMapping("/add-parent-category")
    public ResponseEntity<String> addParentCategory(@RequestBody ParentCategoryDTO parentCategoryDTO) {
        return parentCategoryService.addParentCategory(parentCategoryDTO);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return userService.getProductsById(1L);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories(){
        return categoryService.getCategoriesByParentId(1L);
    }

    @PostMapping("/add-favorite")
    public ResponseEntity<String> addFavoriteProducts(
            @RequestBody MyFavoriteRequest addingRequest) {
        return favoriteService.addMyFavoriteProducts(addingRequest);
    }

    @GetMapping("/my-favorites-products/{page}/{limit}")
    public ResponseEntity<List<SearchedProductDTO>> findMyFavoriteProducts(
            @PathVariable int limit,
            @PathVariable int page) {
        return favoriteService.getAllMyFavoriteProducts(page, limit);
    }

    @DeleteMapping("/delete-favorite")
    public ResponseEntity<String> deleteFavoriteProduct(@RequestBody final MyFavoriteRequest myFavoriteRequest) {
        return favoriteService.deleteMyFavorite(myFavoriteRequest);
    }
}
