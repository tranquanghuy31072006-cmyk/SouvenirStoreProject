package com.learning.souvenirstoreproject.controller;

import com.learning.souvenirstoreproject.dto.request.BrandCreationRequest;
import com.learning.souvenirstoreproject.dto.request.BrandUpdateRequest;
import com.learning.souvenirstoreproject.dto.response.ApiResponse;
import com.learning.souvenirstoreproject.dto.response.BrandResponse;
import com.learning.souvenirstoreproject.repository.BrandRepository;
import com.learning.souvenirstoreproject.service.BrandService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class BrandController {
    BrandRepository brandRepository;
    BrandService brandService;

    //createBrand
    @PostMapping("/add-brand")
    ApiResponse<BrandResponse> createBrand(@RequestBody BrandCreationRequest brandCreationRequest) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.createBrand(brandCreationRequest))
                .build();
    }

    //getBrandById
    @GetMapping("/get-brand-by-id/{brandId}")
    ApiResponse<BrandResponse> getBrandById(@PathVariable Long brandId) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.getBrandById(brandId))
                .build();
    }

    //getActiveBrands
    @GetMapping("/get-active-brands")
    ApiResponse<List<BrandResponse>> getActiveBrands() {
        return ApiResponse.<List<BrandResponse>>builder().result(brandService.getActiveBrands()).build();
    }

    //updateBrand
    @PutMapping("/update-brand/{brandId}")
    ApiResponse<BrandResponse> updateBrand(@PathVariable Long brandId, @RequestBody BrandUpdateRequest brandUpdateRequest) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.updateBrand(brandId, brandUpdateRequest))
                .build();
    }

    //deactivateBrandStatus
    @PutMapping("/deactivate-brand-status/{brandId}")
    ApiResponse<BrandResponse> deactivateBrand(@PathVariable Long brandId) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.deactivateBrand(brandId))
                .build();
    }

    //deleteBrand
    @DeleteMapping("/delete-brand/{brandId}")
    ApiResponse<String> deleteBrand(@PathVariable Long brandId) {
        brandService.deleteBrand(brandId);
        return ApiResponse.<String>builder()
                .message("Brand with id = " + brandId + " has been deleted")
                .build();
    }
}
