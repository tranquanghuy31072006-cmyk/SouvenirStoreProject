package com.learning.souvenirstoreproject.repository;

import com.learning.souvenirstoreproject.entity.ProductVariant;
import com.learning.souvenirstoreproject.enums.ProductVariantStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    List<ProductVariant> findByProductId(Long productId);

    boolean existsBySku(String sku);

    List<ProductVariant> findByProductIdAndStatus(Long productId, ProductVariantStatus status);
}
