package com.nbe8101team03.domain.product.repository;

import com.nbe8101team03.domain.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    //여러 이미지를 동시에 받아오는 메서드
    @Query("select p from ProductImage p where p.id in :ids")
    List<ProductImage> findAllByIdIn(@Param("ids") List<Long> ids);
}
