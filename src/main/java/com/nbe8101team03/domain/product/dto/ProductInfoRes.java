package com.nbe8101team03.domain.product.dto;

import lombok.Builder;

/**
 * product 데이터 조회시 전송될 응답
 * @param id product 의 아이디
 * @param name 상품 이름
 * @param type 상품 타입
 * @param cost 상품 가격
 * @param description 상품 설명
 * @param imageId 이미지 아이디
 */
@Builder
public record ProductInfoRes (
        Long id,
        String name,
        String type,
        Integer cost,
        String description,
        Long imageId
) {
}
