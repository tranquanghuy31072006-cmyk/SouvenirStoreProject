package com.learning.souvenirstoreproject.dto.request;

import com.learning.souvenirstoreproject.entity.ProductImage;
import com.learning.souvenirstoreproject.enums.ProductStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreationRequest {
    @NotBlank
    @Size(max = 150)
    private String name;

    private String description;

    @Positive
    @NotNull
    private BigDecimal price;

    @NotNull
    private Long categoryId;

    private String thumbnail;

    private List<ProductImageCreationRequest> imageUrls;
}
