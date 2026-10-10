package com.learning.souvenirstoreproject.mapper;

import com.learning.souvenirstoreproject.dto.request.PermissionCreationRequest;
import com.learning.souvenirstoreproject.dto.request.PermissionUpdateRequest;
import com.learning.souvenirstoreproject.dto.response.PermissionResponse;
import com.learning.souvenirstoreproject.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionResponse toPermissionResponse(Permission permission);

    Permission toPermission(PermissionCreationRequest request);

    @Mapping(target = "name", ignore = true)
    void updatePermission(@MappingTarget Permission permission, PermissionUpdateRequest request);
}
