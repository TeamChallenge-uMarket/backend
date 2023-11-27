package com.example.securityumarket.models.DTO.chat;

import lombok.Builder;

@Builder
public record ChatNotification(
        Long id,
        Long senderId,
        Long recipientId,
        String content) {
}
