package com.learning.souvenirstoreproject.repository;

import com.learning.souvenirstoreproject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, String> {
    long countRolesByPermissionsName(String permissionsName);
}
