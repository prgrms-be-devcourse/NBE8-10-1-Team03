package com.nbe8101team03.domain.product.servce;

import com.nbe8101team03.domain.product.entity.ProductImage;
import com.nbe8101team03.domain.product.repository.ProductImageRepository;
import com.nbe8101team03.global.exception.errorCode.ProductErrorCode;
import com.nbe8101team03.global.exception.exception.ProductException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    @Transactional
    public Long saveImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("이미지 파일이 비어있음");
        }

        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("이미지 읽기 실패", e);
        }

        ProductImage image = ProductImage.builder()
                .image(bytes)
                .build();

        return productImageRepository.save(image).getId();
    }

    @Transactional(readOnly = true)
    public byte[] loadImage(Long imageId) {
        ProductImage image = productImageRepository.findById(imageId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.UNKNOWN_IMAGE));

        return image.getImage();
    }

    @Transactional(readOnly = true)
    public Map<Long, byte[]> loadImages(List<Long> imageIds) {
        List<ProductImage> images = productImageRepository.findAllByIdIn(imageIds);

        return images.stream()
                .collect(Collectors.toMap(
                        ProductImage::getId,
                        ProductImage::getImage
                ));
    }

}