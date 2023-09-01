package com.example.securityumarket.controllers.mainpage;

import com.example.securityumarket.models.DTO.SearchedProductDTO;
import com.example.securityumarket.models.search.SearchByCategoryRequest;
import com.example.securityumarket.models.search.SearchNameCategoryRequest;
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

    /**
     * 'page' - starts from 0
     * 'limit' - show limit count products on page
     * service finds:
     *          products by part of product name
     *          by categoryIds
     *          by part of product nam + categoryIds
     * and returns list with productDTO
     */
    @GetMapping("/findProductsByName/{page}/{limit}")
    public ResponseEntity<List<SearchedProductDTO>> findProductsByName(
            @RequestBody final SearchRequest searchRequest,
            @PathVariable int limit,
            @PathVariable int page) {
        return searchService.findProductsByName(searchRequest, page, limit);
    }

    @GetMapping("/findProductsByCategory/{page}/{limit}")
    public ResponseEntity<List<SearchedProductDTO>> findProductsByParentCategory(
            @RequestBody final SearchByCategoryRequest searchRequest,
            @PathVariable int limit,
            @PathVariable int page) {
        return searchService.findProductsByCategoryIds(searchRequest, page, limit);
    }

    @GetMapping("/findProductsByNameAndCategory/{page}/{limit}")
    public ResponseEntity<List<SearchedProductDTO>> findProductsByNameAnsCategory(
            @RequestBody final SearchNameCategoryRequest searchRequest,
            @PathVariable int limit,
            @PathVariable int page) {
        return searchService.findProductsByNameAndCategories(searchRequest, page, limit);
    }
}
