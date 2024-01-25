package com.example.securityumarket.dto.entities.user;

import lombok.Builder;

@Builder
public record ResponseUserContactDTO(
        String phone,
        String email){
}
