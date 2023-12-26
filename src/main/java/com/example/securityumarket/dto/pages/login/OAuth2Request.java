package com.example.securityumarket.dto.pages.login;

import lombok.Builder;

@Builder
public record OAuth2Request (String email, String name, String password, String picture){}