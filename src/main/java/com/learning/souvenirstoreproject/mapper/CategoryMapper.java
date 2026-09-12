package com.learning.souvenirstoreproject.mapper;

import com.learning.souvenirstoreproject.dto.response.CategoryResponse;
import com.learning.souvenirstoreproject.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toCategoryResponse (Category category);
}
