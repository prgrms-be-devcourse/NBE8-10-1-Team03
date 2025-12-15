package com.nbe8101team03.domain.product.servce;

import com.nbe8101team03.domain.product.dto.ProductInfoDto;
import com.nbe8101team03.domain.product.dto.ProductInfoRes;
import com.nbe8101team03.domain.product.entity.Product;
import com.nbe8101team03.domain.product.repository.ProductImageRepository;
import com.nbe8101team03.domain.product.repository.ProductRepository;
import com.nbe8101team03.global.exception.errorCode.ProductErrorCode;
import com.nbe8101team03.global.exception.exception.ProductException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 상품 관련 비즈니스 로직을 처리하는 서비스.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    /**
     * 상품을 생성합니다.
     *
     * @param dto 상품 정보
     * @return 생성된 상품 ID
     */
    @Transactional
    public Long createProduct(ProductInfoDto dto) {
        verifyInfo(dto);

        Product product = toEntity(dto);

        try {
            productRepository.save(product);
        } catch (Exception e) {
            productImageRepository.deleteById(dto.imageId());
            throw new ProductException(ProductErrorCode.CREATE_FAIL,
                    "[ProductService#createProduct] create fail");
        }

        return product.getId();
    }

    /**
     * 상품 정보를 수정합니다. 이미지 정보도 수정하면, 기존 이미지를 삭제하고 새로운 이미지로 매핑합니다.
     *
     * @param productId 상품 ID
     * @param dto 수정할 상품 정보
     * @return 수정된 상품 ID
     */
    @Transactional
    public Long updateProduct(Long productId, ProductInfoDto dto) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if(productOpt.isEmpty()) throw new ProductException(ProductErrorCode.UNKNOWN_PRODUCT);

        Product product = productOpt.get();

        if(product.getImageId() != dto.imageId()) {
            productImageRepository.deleteById(product.getImageId());
        }
        product.update(dto);

        //갱신 시도
        try {
            productRepository.save(product);
        } catch (Exception e) { //실패 시 이미지도 같이 삭제
            productImageRepository.deleteById(dto.imageId());
            throw new ProductException(ProductErrorCode.CREATE_FAIL,
                    "[ProductService#updateProduct] update fail", "갱신에 실패했습니다.");
        }

        return product.getId();
    }

    @Transactional
    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

    @Transactional(readOnly = true)
    public List<ProductInfoRes> getProducts() {
        List<Product> lists = productRepository.findAll();

        return lists.stream().map(this::toRes).toList();
    }

    @Transactional(readOnly = true)
    public ProductInfoRes getProduct(Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if(productOpt.isEmpty()) throw new ProductException(ProductErrorCode.UNKNOWN_PRODUCT);

        return toRes(productOpt.get());
    }

    private void verifyInfo(ProductInfoDto dto) {
        if(dto.name() == null) throw new ProductException(ProductErrorCode.CREATE_FAIL, makeErrorMsg("이름"));
        if(dto.cost() == null || dto.cost() <= 0) throw new ProductException(ProductErrorCode.CREATE_FAIL, makeErrorMsg("가격"));
        if(dto.type() == null) throw new ProductException(ProductErrorCode.CREATE_FAIL, makeErrorMsg("타입"));
    }

    private Product toEntity(ProductInfoDto dto) {
        return Product.builder()
                .name(dto.name())
                .type(dto.type())
                .cost(dto.cost())
                .description(dto.description())
                .imageId(dto.imageId())
                .build();
    }

    private ProductInfoRes toRes(Product product) {
        return ProductInfoRes.builder()
                .id(product.getId())
                .name(product.getName())
                .type(product.getType())
                .cost(product.getCost())
                .description(product.getDescription())
                .imageId(product.getImageId())
                .build();
    }

    private String makeErrorMsg(String str) {
        return "[" + str + "] 형식이 잘못되었습니다.";
    }
}
