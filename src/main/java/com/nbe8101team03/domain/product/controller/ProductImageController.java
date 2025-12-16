package com.nbe8101team03.domain.product.controller;

import com.nbe8101team03.domain.product.servce.ProductImageService;
import com.nbe8101team03.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/images")
public class ProductImageController {

    private final ProductImageService productImageService;


    @PostMapping
    public CommonResponse<Long> upload(@RequestParam MultipartFile image) {
        Long imageId = productImageService.saveImage(image);
        return CommonResponse.success(imageId, "이미지 저장 성공");
    }


    @DeleteMapping("/{imageId}")
    public CommonResponse<Void> delete(@PathVariable Long imageId) {
        productImageService.deleteImage(imageId);
        return CommonResponse.success(null, "이미지 삭제 성공");
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long imageId) {
        byte[] image = productImageService.loadImage(imageId);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @GetMapping
    public CommonResponse<Map<Long, byte[]>> getImages(
            @RequestParam List<Long> imageIds
    ) {
        Map<Long, byte[]> images = productImageService.loadImages(imageIds);
        return CommonResponse.success(images, "이미지 목록 조회 성공");
    }
}