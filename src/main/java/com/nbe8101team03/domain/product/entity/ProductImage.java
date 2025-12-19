package com.nbe8101team03.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


/**
 * 상품 이미지를 저장하는 엔티티입니다.
 *
 * <p>
 * 기본적으로 이미지는 클라이언트(프론트엔드)에서 캐싱하여 사용합니다.
 * 클라이언트는 이미지를 요청할 때 이미지의 {@code id}를 기준으로,
 * 이미 저장된 이미지 중 동일한 ID가 존재하면 해당 이미지를 재사용합니다.
 * 만약 일치하는 이미지가 없는 경우에만 서버로부터 이미지를 다시 요청합니다.
 * </p>
 *
 * <p>
 * JPA {@code OneToOne} 연관관계는 {@code LAZY} 로딩이 안정적으로 보장되지 않기 때문에,
 * 해당 엔티티는 {@code Product}와 직접적인 연관관계를 맺지 않고
 * 이미지 ID를 통한 간접 참조 방식으로 설계하였습니다.
 * 이를 통해 불필요한 즉시 로딩과 예기치 않은 쿼리 발생을 방지합니다.
 * </p>
 */
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "product_image")
public class ProductImage {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @CreatedDate
    private LocalDateTime createdAt;
}
