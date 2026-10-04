package com.learning.souvenirstoreproject.mapper;

import com.learning.souvenirstoreproject.dto.request.CategoryRequest;
import com.learning.souvenirstoreproject.dto.response.CategoryResponse;
import com.learning.souvenirstoreproject.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toCategoryResponse (Category category);

    Category toCategory(CategoryRequest categoryRequest);

    void updateCategory(@MappingTarget Category category, CategoryRequest categoryRequest);
}
