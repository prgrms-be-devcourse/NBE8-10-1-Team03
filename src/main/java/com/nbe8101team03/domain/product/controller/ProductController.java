package com.nbe8101team03.domain.product.controller;

import com.nbe8101team03.domain.product.dto.ProductInfoDto;
import com.nbe8101team03.domain.product.dto.ProductInfoRes;
import com.nbe8101team03.domain.product.servce.ProductService;
import com.nbe8101team03.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 생성
     */
    @PostMapping
    public CommonResponse<Long> create(@RequestBody ProductInfoDto dto) {
        Long id = productService.createProduct(dto);
        return CommonResponse.success(id, "상품 생성 성공");
    }

    /**
     * 상품 수정
     */
    @PutMapping("/{productId}")
    public CommonResponse<Long> update(
            @PathVariable Long productId,
            @RequestBody ProductInfoDto dto
    ) {
        Long id = productService.updateProduct(productId, dto);
        return CommonResponse.success(id, "상품 수정 성공");
    }

    /**
     * 상품 삭제
     */
    @DeleteMapping("/{productId}")
    public CommonResponse<Void> delete(@PathVariable Long productId) {
        productService.delete(productId);
        return CommonResponse.success(null, "상품 삭제 성공");
    }

    /**
     * 상품 전체 조회
     */
    @GetMapping
    public CommonResponse<List<ProductInfoRes>> getProducts() {
        return CommonResponse.success(
                productService.getProducts(),
                "상품 목록 조회 성공"
        );
    }

    /**
     * 상품 단건 조회
     */
    @GetMapping("/{productId}")
    public CommonResponse<ProductInfoRes> getProduct(@PathVariable Long productId) {
        return CommonResponse.success(
                productService.getProduct(productId),
                "상품 조회 성공"
        );
    }
}