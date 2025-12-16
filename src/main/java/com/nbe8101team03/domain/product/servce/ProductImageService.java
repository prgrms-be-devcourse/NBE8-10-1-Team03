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
            throw new ProductException(ProductErrorCode.UNKNOWN_IMAGE,
                    "[ProductImageService#saveImage] no input image", "입력된 이미지가 없습니다.");
        }

        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new ProductException(ProductErrorCode.UNKNOWN_IMAGE,
                    "[ProductImageService#saveImage] decoding fail", "이미지 형식이 올바르지 않습니다.");
        }

        ProductImage image = ProductImage.builder()
                .image(bytes)
                .build();

        return productImageRepository.save(image).getId();
    }

    @Transactional
    public void deleteImage(Long id) {
        productImageRepository.deleteById(id);
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