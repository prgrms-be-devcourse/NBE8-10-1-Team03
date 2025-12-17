package com.nbe8101team03.domain.product.controller;


import com.nbe8101team03.domain.product.dto.ProductDetailInfoRes;
import com.nbe8101team03.domain.product.dto.ProductInfoDto;
import com.nbe8101team03.domain.product.servce.ProductService;
import com.nbe8101team03.global.response.CommonResponse;
import com.nbe8101team03.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;

    /**
     * 상품 생성
     */
    @PostMapping
    public ResponseEntity<Response<Long>> create(@RequestBody ProductInfoDto dto) {
        Long id = productService.createProduct(dto);
        return ResponseEntity.ok(CommonResponse.success(id, "상품 생성 성공"));
    }

    /**
     * 상품 수정
     */
    @PutMapping("/{productId}")
    public ResponseEntity<Response<Long>> update(
            @PathVariable Long productId,
            @RequestBody ProductInfoDto dto
    ) {
        Long id = productService.updateProduct(productId, dto);
        return ResponseEntity.ok(CommonResponse.success(id, "상품 수정 성공"));
    }

    /**
     * 상품 삭제
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Response<Void>> delete(@PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.ok(CommonResponse.success(null, "상품 삭제 성공"));
    }

    @GetMapping
    public ResponseEntity<Response<List<ProductDetailInfoRes>>> getProductsDetail() {
        return ResponseEntity.ok(CommonResponse.success(
                productService.getProductsDetail(),
                "상품 목록 조회 성공"
        ));
    }

}
