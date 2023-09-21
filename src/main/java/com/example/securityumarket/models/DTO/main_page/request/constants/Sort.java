package com.example.securityumarket.models.DTO.main_page.request.constants;

import lombok.Getter;

@Getter
public enum Sort {
    DATE_DESC("c.created desc"),
    DATE_ASC("c.created asc"),
    PRICE_DESC("c.price desc"),
    PRICE_ASC("c.price asc");

    private final String value;

    Sort(String value) {
        this.value = value;
    }
}
