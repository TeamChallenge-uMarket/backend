package com.example.securityumarket.dto.chat;

import lombok.Builder;

@Builder
public record ChatNotification(
        Long id,
        Long senderId,
        Long recipientId,
        Long carId,
        String content) {
}
