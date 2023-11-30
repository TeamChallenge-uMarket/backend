package com.example.securityumarket.models.DTO.login_page;

import lombok.Builder;

@Builder
public record OAuth2Request (String email, String name, String password, String picture){}