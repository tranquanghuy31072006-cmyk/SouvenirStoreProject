package com.learning.souvenirstoreproject.controller;

import com.learning.souvenirstoreproject.dto.request.PermissionCreationRequest;
import com.learning.souvenirstoreproject.dto.request.PermissionUpdateRequest;
import com.learning.souvenirstoreproject.dto.response.ApiResponse;
import com.learning.souvenirstoreproject.dto.response.PermissionResponse;
import com.learning.souvenirstoreproject.service.PermissionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;

    @PostMapping("/permissions")
    ApiResponse<PermissionResponse> createPermission(@Valid @RequestBody PermissionCreationRequest request) {

        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.createPermission(request))
                .build();
    }

    @GetMapping("/permissions")
    ApiResponse<List<PermissionResponse>> getAllPermission() {

        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAllPermissions())
                .build();
    }

    @GetMapping("/permissions/{permissionName}")
    ApiResponse<PermissionResponse> getPermission(@PathVariable String permissionName) {

        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.getPermission(permissionName))
                .build();
    }

    @PutMapping("/permissions/{permissionName}")
    ApiResponse<PermissionResponse> updatePermission(@PathVariable String permissionName,
                                                     @Valid @RequestBody PermissionUpdateRequest request) {

        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.updatePermission(permissionName, request))
                .build();
    }

    @DeleteMapping("/permissions/{permissionName}")
    ApiResponse<String> deletePermission(@PathVariable String permissionName) {

        permissionService.deletePermission(permissionName);

        return ApiResponse.<String>builder()
                .result("Permission deleted")
                .build();
    }

    @GetMapping("/roles/{roleName}/permissions")
    ApiResponse<List<PermissionResponse>> getPermissionsOfRole(@PathVariable String roleName) {

        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getPermissionOfRole(roleName))
                .build();
    }

    @PostMapping("/roles/{roleName}/permissions/{permissionName}")
    ApiResponse<String> assignPermission(@PathVariable String roleName, @PathVariable String permissionName) {

        permissionService.assignPermissionToRole(roleName, permissionName);

        return ApiResponse.<String>builder()
                .message("Permission assigned")
                .build();
    }

    @DeleteMapping("/roles/{roleName}/permissions/{permissionName}")
    ApiResponse<String> deletePermission(@PathVariable String roleName, @PathVariable String permissionName) {

        permissionService.removePermissionFromRole(roleName, permissionName);

        return ApiResponse.<String>builder()
                .message("Permission removed")
                .build();
    }
}
