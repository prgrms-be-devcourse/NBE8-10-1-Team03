package com.nbe8101team03.domain.product.controller;

import com.nbe8101team03.domain.product.servce.ProductImageService;
import com.nbe8101team03.global.response.CommonResponse;
import com.nbe8101team03.global.response.Response;
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
    public ResponseEntity<Response<String>> upload(@RequestParam MultipartFile image) {
        String imageId = productImageService.saveImage(image);
        return ResponseEntity.ok(CommonResponse.success(imageId, "이미지 저장 성공"));
    }


    @DeleteMapping("/{imageId}")
    public ResponseEntity<Response<Void>> delete(@PathVariable String imageId) {
        productImageService.deleteImage(imageId);
        return ResponseEntity.ok(CommonResponse.success(null, "이미지 삭제 성공"));
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageId) {
        byte[] image = productImageService.loadImage(imageId);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @GetMapping
    public ResponseEntity<Response<Map<String, byte[]>>> getImages(
            @RequestParam List<String> imageIds
    ) {
        Map<String, byte[]> images = productImageService.loadImages(imageIds);
        return ResponseEntity.ok(CommonResponse.success(images, "이미지 목록 조회 성공"));
    }
}