package com.example.securityumarket.services.main;

import com.example.securityumarket.dao.ProductCategoryDAO;
import com.example.securityumarket.dao.ProductDAO;
import com.example.securityumarket.dao.ProductGalleryDAO;
import com.example.securityumarket.models.DTO.SearchedProductDTO;
import com.example.securityumarket.models.entities.Product;
import com.example.securityumarket.models.search.FullSearchRequest;
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
    private final ProductCategoryDAO productCategoryDAO;


    public ResponseEntity<List<SearchedProductDTO>> findProductsByNameAndCategories(FullSearchRequest searchRequest, int page, int limit) {
        PageRequest pageable = PageRequest.of(page, limit);
        String searchQuery = searchRequest.getSearchQuery();
        List<Long> categoriesId = searchRequest.getCategoriesId();
        String status = searchRequest.getStatus().name();
        String orderBy = searchRequest.getOrderBy().name();
        String sortBy = searchRequest.getSortBy().name();

        if (categoriesId.isEmpty()) {
            return ResponseEntity.ok(convertProductToSearchedProductDTO(productDAO.findProductByName(
                    searchQuery,
                    status,
                    orderBy,
                    sortBy,
                    pageable)));
        }

        List<Product> allByNameAndCategory = productCategoryDAO.findAllByNameAndCategory(
                categoriesId,
                searchQuery,
                status,
                orderBy,
                sortBy,
                pageable);

        return ResponseEntity.ok(convertProductToSearchedProductDTO(allByNameAndCategory));
    }


    private List<SearchedProductDTO> convertProductToSearchedProductDTO(List<Product> productList) {

        return productList.stream()
                .map(product -> SearchedProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .imgUrl(productGalleryDAO.findByProductIdAndIsMain(product.getId(), true).getUrl())
                        .status(product.getStatus().toString())
                        .build())
                .collect(Collectors.toList());
    }
}
