package com.learning.souvenirstoreproject.mapper;

import com.learning.souvenirstoreproject.dto.request.ProductCreationRequest;
import com.learning.souvenirstoreproject.dto.request.ProductUpdateRequest;
import com.learning.souvenirstoreproject.dto.response.ProductResponse;
import com.learning.souvenirstoreproject.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toProductResponse(Product product);

    Product toProduct(ProductCreationRequest productCreationRequest);

    void updateProduct(@MappingTarget Product product, ProductUpdateRequest productUpdateRequest);

    List<ProductResponse> toProductResponseList(List<Product> productList);
}
