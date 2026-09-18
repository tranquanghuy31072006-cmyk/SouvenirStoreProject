package com.learning.souvenirstoreproject.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.learning.souvenirstoreproject.enums.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVariantResponse {
    Long id;
    String sku;
    String size;
    String color;
    Integer stockQuantity;
    BigDecimal price;
    BigDecimal salePrice;
    String image;
    ProductStatus status;
}
