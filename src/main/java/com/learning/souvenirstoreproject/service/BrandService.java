package com.learning.souvenirstoreproject.service;

import com.learning.souvenirstoreproject.dto.request.BrandCreationRequest;
import com.learning.souvenirstoreproject.dto.request.BrandUpdateRequest;
import com.learning.souvenirstoreproject.dto.response.BrandResponse;
import com.learning.souvenirstoreproject.entity.Brand;
import com.learning.souvenirstoreproject.enums.BrandStatus;
import com.learning.souvenirstoreproject.exception.AppException;
import com.learning.souvenirstoreproject.exception.ErrorCode;
import com.learning.souvenirstoreproject.mapper.BrandMapper;
import com.learning.souvenirstoreproject.repository.BrandRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandService {
    BrandRepository brandRepository;
    BrandMapper brandMapper;

    //createBrand
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public BrandResponse createBrand(BrandCreationRequest brandCreationRequest) {
        if (brandRepository.existsByName(brandCreationRequest.getName()))
            throw new AppException(ErrorCode.BRAND_EXISTED);

        Brand brand = brandMapper.toBrand(brandCreationRequest);

        try {
            brandRepository.save(brand);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.BRAND_EXISTED);
        }

        return brandMapper.toBrandResponse(brand);
    }

    //getBrandById
    public BrandResponse getBrandById(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
        return brandMapper.toBrandResponse(brand);
    }

    //getActiveBrands
    public List<BrandResponse> getActiveBrands() {
        return brandRepository.getBrandByStatus(BrandStatus.ACTIVE).stream()
                .map(brandMapper::toBrandResponse).collect(Collectors.toList());
    }

    //updateBrand
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public BrandResponse updateBrand(Long id, BrandUpdateRequest brandUpdateRequest) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));

        brandMapper.updateBrand(brand, brandUpdateRequest);
        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public BrandResponse activateBrand(Long id) {
        Brand brand =  brandRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
        if (brand.getStatus() == BrandStatus.ACTIVE)
            throw new AppException(ErrorCode.BRAND_ALREADY_ACTIVE);
        brand.setStatus(BrandStatus.ACTIVE);
        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    //deactivateBrand
    @PreAuthorize("hasRole('ADMIN')")
    public BrandResponse deactivateBrand(Long id) {
        Brand brand =  brandRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
        if (brand.getStatus() == BrandStatus.INACTIVE)
            throw new AppException(ErrorCode.BRAND_ALREADY_INACTIVE);
        brand.setStatus(BrandStatus.INACTIVE);
        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    //deleteBrand
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
        brandRepository.delete(brand);
    }
}
