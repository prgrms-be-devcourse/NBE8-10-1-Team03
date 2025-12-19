package com.nbe8101team03.domain.product.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProductDetailInfoRes (
        Long id,
        String name,
        String type,
        Integer cost,
        String description,
        String imageId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long adminId

) {
}
