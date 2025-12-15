package com.nbe8101team03.domain.product.dto;

import lombok.Builder;

@Builder
public record ProductInfoRes(
        Long id,
        String name,
        String type,
        Integer cost,
        String description,
        Long imageId
) {
}
