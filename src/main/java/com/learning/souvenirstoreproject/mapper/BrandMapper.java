package com.learning.souvenirstoreproject.mapper;

import com.learning.souvenirstoreproject.dto.request.BrandCreationRequest;
import com.learning.souvenirstoreproject.dto.request.BrandUpdateRequest;
import com.learning.souvenirstoreproject.dto.response.BrandResponse;
import com.learning.souvenirstoreproject.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toBrand(BrandCreationRequest brandCreationRequest);

    BrandResponse toBrandResponse(Brand brand);

    void updateBrand(@MappingTarget Brand brand, BrandUpdateRequest brandUpdateRequest);
}
