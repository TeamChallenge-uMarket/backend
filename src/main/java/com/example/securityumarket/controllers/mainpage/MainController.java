package com.example.securityumarket.controllers.mainpage;

import com.example.securityumarket.models.DTO.CategoryDTO;
import com.example.securityumarket.models.DTO.ParentCategoryDTO;
import com.example.securityumarket.models.DTO.ProductDTO;
import com.example.securityumarket.models.entities.Product;
import com.example.securityumarket.services.main.CategoryService;
import com.example.securityumarket.services.main.ParentCategoryService;
import com.example.securityumarket.services.main.ProductService;
import com.example.securityumarket.services.main.UserService;
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

}
