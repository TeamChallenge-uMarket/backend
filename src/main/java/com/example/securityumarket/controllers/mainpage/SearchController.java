package com.example.securityumarket.controllers.mainpage;

import com.example.securityumarket.models.DTO.ProductByNameDTO;
import com.example.securityumarket.models.search.SearchByCategoryRequest;
import com.example.securityumarket.models.search.SearchRequest;
import com.example.securityumarket.services.main.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/main/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    //TODO write doc

    /**
     * 'page' - starts from 0
     * 'limit' - show limit count products on page
     * service finds products by part of name products and returns list with productDTO
     */
    @GetMapping("/findProductsByName/{page}/{limit}")
    public ResponseEntity<List<ProductByNameDTO>> findProductsByName(
            @RequestBody final SearchRequest searchRequest,
            @PathVariable int limit,
            @PathVariable int page) {
        return searchService.findLimitProducts(searchRequest, page, limit);
    }


    /**
     * 'page' - starts from 0
     * 'limit' - show limit count products on page
     * service finds products by category of products and returns list with productDTO
     */
    @GetMapping("/findProductsByCategory/{page}/{limit}")
    public ResponseEntity<List<ProductByNameDTO>> findProductsByParentCategory(
            @RequestBody final SearchByCategoryRequest searchRequest,
            @PathVariable int limit,
            @PathVariable int page) {
        return searchService.findByCategories(searchRequest, page, limit);
    }
}
