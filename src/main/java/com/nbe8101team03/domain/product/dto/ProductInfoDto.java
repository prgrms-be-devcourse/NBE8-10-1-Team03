package com.nbe8101team03.domain.product.dto;


public record ProductInfoDto(
        String name,
        String type,
        Integer cost,
        String description,
        Long imageId
) {
}
