package com.example.securityumarket.services.main;

import com.example.securityumarket.dao.*;
import com.example.securityumarket.models.DTO.ProductByNameDTO;
import com.example.securityumarket.models.entities.Category;
import com.example.securityumarket.models.entities.Product;
import com.example.securityumarket.models.search.SearchByCategoryRequest;
import com.example.securityumarket.models.search.SearchRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchService {

    private final ProductDAO productDAO;
    private final ProductGalleryDAO productGalleryDAO;
    private final CategoryDAO categoryDAO;
    private final ProductCategoryDAO productCategoryDAO;

    public ResponseEntity<List<ProductByNameDTO>> findLimitProducts(SearchRequest searchRequest, int page, int limit) {
        String searchField = searchRequest.getSearchField();
        PageRequest pageable = PageRequest.of(page, limit);

        List<Product> products = productDAO.findProductByNameIsContainingIgnoreCase(searchField, pageable);

        return ResponseEntity.ok(getProductsDto(products));
    }

    public ResponseEntity<List<ProductByNameDTO>> findByCategories(SearchByCategoryRequest searchRequest, int page, int limit) {
        List<Long> categoryIds = searchRequest.getCategoriesId();
        PageRequest pageable = PageRequest.of(page, limit);

        List<Category> categoryList = categoryDAO.findCategoryByIdIn(categoryIds);

        List<Product> productsByCategories = productCategoryDAO.findProductsByCategories(categoryList, pageable);

        return ResponseEntity.ok(getProductsDto(productsByCategories));
    }


    private List<ProductByNameDTO> getProductsDto(List<Product> productList) {

        return productList.stream()
                .map(product -> ProductByNameDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .imgUrl(productGalleryDAO.findByProductIdAndIsMain(product.getId(), true).getUrl())
                        .build())
                .collect(Collectors.toList());
    }
}
