package com.example.securityumarket.services.common;

import com.example.securityumarket.dao.ProductGalleryDAO;
import com.example.securityumarket.models.DTO.SearchedProductDTO;
import com.example.securityumarket.models.entities.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommonService {
    private final ProductGalleryDAO productGalleryDAO;

    public List<SearchedProductDTO> convertProductToSearchedProductDTO(List<Product> productList) {

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
