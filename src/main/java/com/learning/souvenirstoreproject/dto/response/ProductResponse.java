package com.learning.souvenirstoreproject.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String status;
    private String categoryName;
    private String categorySlug;
    private List<ProductImageResponse> images;
}
