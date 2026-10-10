package com.learning.souvenirstoreproject.service;

import com.learning.souvenirstoreproject.dto.request.PermissionCreationRequest;
import com.learning.souvenirstoreproject.dto.request.PermissionUpdateRequest;
import com.learning.souvenirstoreproject.dto.response.PermissionResponse;
import com.learning.souvenirstoreproject.entity.Permission;
import com.learning.souvenirstoreproject.entity.Role;
import com.learning.souvenirstoreproject.exception.AppException;
import com.learning.souvenirstoreproject.exception.ErrorCode;
import com.learning.souvenirstoreproject.mapper.PermissionMapper;
import com.learning.souvenirstoreproject.repository.PermissionRepository;
import com.learning.souvenirstoreproject.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    RoleRepository roleRepository;
    PermissionMapper permissionMapper;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse createPermission(PermissionCreationRequest request) {
        if (permissionRepository.existsById(request.getName()))
            throw new AppException(ErrorCode.PERMISSION_ALREADY_EXISTS);

        Permission permission = permissionMapper.toPermission(request);

        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<PermissionResponse> getAllPermissions() {

        return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse getPermission(String name) {

        Permission permission = permissionRepository.findById(name).orElseThrow(()
                -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        return permissionMapper.toPermissionResponse(permission);
    }

    @Transactional()
    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse updatePermission(String name, PermissionUpdateRequest request) {
        Permission permission = permissionRepository.findById(name).orElseThrow(()
                -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        permissionMapper.updatePermission(permission, request);

        permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    @Transactional()
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePermission(String name) {
        Permission permission = permissionRepository.findById(name).orElseThrow(()
                -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        long roleCount = roleRepository.countRolesByPermissionsName(name);

        if (roleCount > 0)
            throw new AppException(ErrorCode.PERMISSION_IN_USE);
        else
            permissionRepository.delete(permission);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<PermissionResponse> getPermissionOfRole(String roleName) {
        Role role = roleRepository.findById(roleName).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        return role.getPermissions().stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void assignPermissionToRole(String roleName, String permissionName) {
        Role role = roleRepository.findById(roleName).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        Permission permission = permissionRepository.findById(permissionName).orElseThrow(()
                -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        if (role.getPermissions().contains(permission))
            throw new AppException(ErrorCode.PERMISSION_ALREADY_ASSIGNED);

        role.getPermissions().add(permission);

        roleRepository.save(role);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void removePermissionFromRole(String roleName, String permissionName) {
        Role role = roleRepository.findById(roleName).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        Permission permission = permissionRepository.findById(permissionName).orElseThrow(()
                -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        if (!role.getPermissions().contains(permission))
            throw new AppException(ErrorCode.PERMISSION_NOT_IN_ROLE);

        role.getPermissions().remove(permission);

        roleRepository.save(role);
    }
}
