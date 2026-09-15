package com.learning.souvenirstoreproject.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItemResponse {
    private Long itemId;

    private Long productId;

    private String productName;

    private String productSlug;

    private BigDecimal price;

    private Integer quantity;
}
