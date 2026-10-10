package com.learning.souvenirstoreproject.repository;

import com.learning.souvenirstoreproject.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    boolean existsByProductVariantId(Long productVariantId);
}
