package com.learning.souvenirstoreproject.mapper;

import com.learning.souvenirstoreproject.dto.request.CategoryCreationRequest;
import com.learning.souvenirstoreproject.dto.request.CategoryUpdateRequest;
import com.learning.souvenirstoreproject.dto.response.CategoryResponse;
import com.learning.souvenirstoreproject.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toCategoryResponse (Category category);
    Category toCategory(CategoryCreationRequest categoryCreationRequest);
    void updateCategory(@MappingTarget Category category, CategoryUpdateRequest categoryUpdateRequest);
}
