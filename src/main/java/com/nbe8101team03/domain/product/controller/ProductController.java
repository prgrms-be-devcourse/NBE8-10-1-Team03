package com.nbe8101team03.domain.product.controller;

import com.nbe8101team03.domain.product.dto.ProductInfoRes;
import com.nbe8101team03.domain.product.servce.ProductService;
import com.nbe8101team03.global.response.CommonResponse;
import com.nbe8101team03.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


    /**
     * 상품 전체 조회
     */
    @GetMapping
    public ResponseEntity<Response<List<ProductInfoRes>>> getProducts() {
        return ResponseEntity.ok(CommonResponse.success(
                productService.getProducts(),
                "상품 목록 조회 성공"
        ));
    }

    /**
     * 상품 단건 조회
     */
    @GetMapping("/{productId}")
    public ResponseEntity<Response<ProductInfoRes>> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(CommonResponse.success(
                productService.getProduct(productId),
                "상품 조회 성공"
        ));
    }
}