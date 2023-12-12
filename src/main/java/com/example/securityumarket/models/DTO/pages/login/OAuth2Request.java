package com.example.securityumarket.models.DTO.pages.login;

import lombok.Builder;

@Builder
public record OAuth2Request (String email, String name, String password, String picture){}