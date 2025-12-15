package com.nbe8101team03.domain.product.entity;


import com.nbe8101team03.domain.product.dto.ProductInfoDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "cost", nullable = false)
    private Integer cost;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "image_id")
    private Long imageId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void update(ProductInfoDto dto) {
        if (name != null) this.name = dto.name();
        if (type != null) this.type = dto.type();
        if (cost != null) this.cost = dto.cost();
        this.description = dto.description();
        if (imageId != null) this.imageId = dto.imageId();
    }


}
