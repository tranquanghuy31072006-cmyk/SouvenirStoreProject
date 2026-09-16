package com.learning.souvenirstoreproject.service;

import com.learning.souvenirstoreproject.dto.request.CategoryCreationRequest;
import com.learning.souvenirstoreproject.dto.request.CategoryUpdateRequest;
import com.learning.souvenirstoreproject.dto.response.CategoryResponse;
import com.learning.souvenirstoreproject.entity.Category;
import com.learning.souvenirstoreproject.enums.CategoryStatus;
import com.learning.souvenirstoreproject.exception.AppException;
import com.learning.souvenirstoreproject.exception.ErrorCode;
import com.learning.souvenirstoreproject.mapper.CategoryMapper;
import com.learning.souvenirstoreproject.repository.CategoryRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    //createCategory
    public CategoryResponse createCategory(CategoryCreationRequest request) {

        if (categoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        Category category = categoryMapper.toCategory(request);

        try {
            category = categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        return categoryMapper.toCategoryResponse(category);
    }

    //getCategoryById
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        return categoryMapper.toCategoryResponse(category);
    }

    //getAllActiveCategory
    public List<CategoryResponse> getAllActiveCategory() {
        return categoryRepository.findAllByStatus(CategoryStatus.ACTIVE).stream()
                .map(categoryMapper::toCategoryResponse).toList();
    }

    //updateCategory
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest categoryUpdateRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        categoryMapper.updateCategory(category, categoryUpdateRequest);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    //deleteCategory
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
