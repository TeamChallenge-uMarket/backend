package com.example.securityumarket.dto.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransportGalleryDTO {

    private Long transportGalleryId;

    private String transportGalleryUrl;
}
