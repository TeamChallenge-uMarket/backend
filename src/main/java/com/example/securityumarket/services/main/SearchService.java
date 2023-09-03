package com.example.securityumarket.services.main;

import com.example.securityumarket.dao.ProductCategoryDAO;
import com.example.securityumarket.dao.ProductDAO;
import com.example.securityumarket.models.DTO.SearchedProductDTO;
import com.example.securityumarket.models.entities.Product;
import com.example.securityumarket.models.search.FullSearchRequest;
import com.example.securityumarket.services.common.CommonService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SearchService {

    private final ProductDAO productDAO;
    private final ProductCategoryDAO productCategoryDAO;
    private final CommonService commonService;


    public ResponseEntity<List<SearchedProductDTO>> findProductsByNameAndCategories(FullSearchRequest searchRequest, int page, int limit) {
        PageRequest pageable = PageRequest.of(page, limit);

        if (searchRequest.getCategoriesId().isEmpty()) {
            return ResponseEntity.ok(commonService.convertProductToSearchedProductDTO(productDAO.findProductsByName(searchRequest, pageable)));
        }

        List<Product> allByNameAndCategory = productCategoryDAO.findProductsByNameAndCategory(searchRequest, pageable);

        return ResponseEntity.ok(commonService.convertProductToSearchedProductDTO(allByNameAndCategory));
    }

}
