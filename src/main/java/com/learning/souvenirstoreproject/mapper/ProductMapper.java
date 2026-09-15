package com.learning.souvenirstoreproject.mapper;

import com.learning.souvenirstoreproject.dto.response.ProductResponse;
import com.learning.souvenirstoreproject.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryName",source = "category.name")
    //@Mapping(target = "categorySlug", source = "category.slug")
    ProductResponse toProductResponse(Product product);
}
