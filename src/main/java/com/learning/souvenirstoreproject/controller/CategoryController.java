package com.learning.souvenirstoreproject.controller;

import com.learning.souvenirstoreproject.dto.request.CategoryRequest;
import com.learning.souvenirstoreproject.dto.response.ApiResponse;
import com.learning.souvenirstoreproject.dto.response.CategoryResponse;
import com.learning.souvenirstoreproject.repository.CategoryRepository;
import com.learning.souvenirstoreproject.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class CategoryController {
    CategoryRepository categoryRepository;
    CategoryService categoryService;

    @PostMapping("/create-category")
    ApiResponse<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createCategory(categoryRequest))
                .build();
    }

    @GetMapping("/get-category/{categoryId}")
    ApiResponse<CategoryResponse> getCategoryById(@PathVariable Long categoryId) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.getCategoryById(categoryId))
                .build();
    }

    @GetMapping("/get-active-category")
    ApiResponse<List<CategoryResponse>> getActiveCategory() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAllActiveCategory())
                .build();
    }

    @PutMapping("/update-category/{categoryId}")
    ApiResponse<CategoryResponse> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryRequest categoryRequest) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategory(categoryId, categoryRequest))
                .build();
    }

    @PutMapping("/activate-category-status/{categoryId}")
    ApiResponse<CategoryResponse> activateCategoryStatus(@PathVariable Long categoryId) {
        categoryService.deactivateCategory(categoryId);
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.activateCategory(categoryId))
                .build();
    }

    @PutMapping("/deactivate-category-status/{categoryId}")
    ApiResponse<CategoryResponse> deactivateCategoryStatus(@PathVariable Long categoryId) {
        categoryService.deactivateCategory(categoryId);
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.deactivateCategory(categoryId))
                .build();
    }

    @DeleteMapping("delete-category-status/{categoryId}")
    ApiResponse<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategoryStatus(categoryId);
        return ApiResponse.<String>builder()
                .message("Category with id " + categoryId + " has been deleted successfully")
                .build();
    }
}
