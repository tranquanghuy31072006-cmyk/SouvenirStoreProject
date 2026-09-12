package com.learning.souvenirstoreproject.repository;

import com.learning.souvenirstoreproject.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,String> {
}
