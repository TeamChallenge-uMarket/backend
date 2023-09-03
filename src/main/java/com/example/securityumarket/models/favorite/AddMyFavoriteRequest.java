package com.example.securityumarket.models.favorite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddMyFavoriteRequest {
    private List<Long> myFavProductIds;
}
