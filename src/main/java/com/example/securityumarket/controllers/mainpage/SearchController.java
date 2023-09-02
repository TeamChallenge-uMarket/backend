package com.example.securityumarket.controllers.mainpage;

import com.example.securityumarket.models.DTO.SearchedProductDTO;
import com.example.securityumarket.models.search.FullSearchRequest;
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

    @GetMapping("/findProductsByNameAndCategory/{page}/{limit}")
    public ResponseEntity<List<SearchedProductDTO>> findProductsByNameAnsCategory(
            @RequestBody final FullSearchRequest searchRequest,
            @PathVariable int limit,
            @PathVariable int page) {
        return searchService.findProductsByNameAndCategories(searchRequest, page, limit);
    }
}
