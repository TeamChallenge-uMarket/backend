package com.example.securityumarket.dto.pages.main.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ResponseCityDTO {

    private Long regionId;

    private List<CityDTO> cities = new ArrayList<>();

}
