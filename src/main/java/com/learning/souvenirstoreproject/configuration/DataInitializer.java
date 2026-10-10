package com.learning.souvenirstoreproject.configuration;

import com.learning.souvenirstoreproject.entity.Permission;
import com.learning.souvenirstoreproject.entity.Role;
import com.learning.souvenirstoreproject.exception.AppException;
import com.learning.souvenirstoreproject.exception.ErrorCode;
import com.learning.souvenirstoreproject.repository.PermissionRepository;
import com.learning.souvenirstoreproject.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataInitializer implements ApplicationRunner {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_STAFF = "STAFF";
    private static final String ROLE_CUSTOMER = "CUSTOMER";

    private static final List<String> ADMIN_PERMISSIONS = List.of(
            "PRODUCT_READ", "PRODUCT_CREATE", "PRODUCT_UPDATE", "PRODUCT_DELETE",
            "VARIANT_READ", "VARIANT_CREATE", "VARIANT_UPDATE", "VARIANT_DELETE",
            "CATEGORY_READ", "CATEGORY_CREATE", "CATEGORY_UPDATE", "CATEGORY_DELETE",
            "BRAND_READ", "BRAND_CREATE", "BRAND_UPDATE", "BRAND_DELETE",
            "ORDER_CREATE", "ORDER_READ_OWN", "ORDER_CANCEL_OWN", "ORDER_MANAGE",
            "CART_USE",
            "USER_READ_OWN", "USER_UPDATE_OWN", "USER_MANAGE"
    );

    private static final List<String> STAFF_PERMISSIONS = List.of(
            "PRODUCT_READ", "PRODUCT_CREATE", "PRODUCT_UPDATE",
            "VARIANT_READ", "VARIANT_CREATE", "VARIANT_UPDATE",
            "CATEGORY_READ", "CATEGORY_CREATE", "CATEGORY_UPDATE",
            "BRAND_READ", "BRAND_CREATE", "BRAND_UPDATE",
            "ORDER_MANAGE"
    );

    private static final List<String> CUSTOMER_PERMISSIONS = List.of(
            "PRODUCT_READ",
            "VARIANT_READ",
            "ORDER_CREATE", "ORDER_READ_OWN", "ORDER_CANCEL_OWN",
            "CART_USE",
            "USER_READ_OWN", "USER_UPDATE_OWN"
    );

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        seedRoles();
        seedPermissions();
        assignPermissionsToRoles();

        log.info("Role and permission initialization completed.");
    }

    private void seedRoles() {
        createRoleIfNotExists(ROLE_ADMIN, "Administrator");
        createRoleIfNotExists(ROLE_STAFF, "Staff member");
        createRoleIfNotExists(ROLE_CUSTOMER, "Customer");
    }

    private void createRoleIfNotExists(String name, String description) {
        if (roleRepository.findById(name).isEmpty()) {
            Role role = Role.builder()
                    .name(name)
                    .description(description)
                    .build();

            roleRepository.save(role);

            log.info("Seeded role: {}", name);
        }
    }

    private void seedPermissions() {
        Map<String, String> defaults = Map.ofEntries(
                Map.entry("PRODUCT_READ", "View products"),
                Map.entry("PRODUCT_CREATE", "Create products"),
                Map.entry("PRODUCT_UPDATE", "Update products"),
                Map.entry("PRODUCT_DELETE", "Delete products"),

                Map.entry("VARIANT_READ", "View product variants"),
                Map.entry("VARIANT_CREATE", "Create product variants"),
                Map.entry("VARIANT_UPDATE", "Update product variants"),
                Map.entry("VARIANT_DELETE", "Delete product variants"),

                Map.entry("CATEGORY_READ", "View categories"),
                Map.entry("CATEGORY_CREATE", "Create categories"),
                Map.entry("CATEGORY_UPDATE", "Update categories"),
                Map.entry("CATEGORY_DELETE", "Delete categories"),

                Map.entry("BRAND_READ", "View brands"),
                Map.entry("BRAND_CREATE", "Create brands"),
                Map.entry("BRAND_UPDATE", "Update brands"),
                Map.entry("BRAND_DELETE", "Delete brands"),

                Map.entry("ORDER_CREATE", "Create orders"),
                Map.entry("ORDER_READ_OWN", "View own orders"),
                Map.entry("ORDER_CANCEL_OWN", "Cancel own orders"),
                Map.entry("ORDER_MANAGE", "Manage all orders"),

                Map.entry("CART_USE", "Use the shopping cart"),

                Map.entry("USER_READ_OWN", "View own profile"),
                Map.entry("USER_UPDATE_OWN", "Update own profile"),
                Map.entry("USER_MANAGE", "Manage user accounts")
        );

        defaults.forEach(this::createPermissionIfNotExists);
    }

    private void createPermissionIfNotExists(String name, String description) {
        if (permissionRepository.findById(name).isEmpty()) {
            Permission permission = Permission.builder()
                    .name(name)
                    .description(description)
                    .build();

            permissionRepository.save(permission);

            log.info("Seeded permission: {}", name);
        }
    }

    private void assignPermissionsToRoles() {
        assignPermissionsToRole(ROLE_ADMIN, ADMIN_PERMISSIONS);
        assignPermissionsToRole(ROLE_STAFF, STAFF_PERMISSIONS);
        assignPermissionsToRole(ROLE_CUSTOMER, CUSTOMER_PERMISSIONS);
    }

    private void assignPermissionsToRole(String roleName, List<String> permissionNames) {
        Role role = roleRepository.findById(roleName).orElseThrow(()
                -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        List<Permission> permissions = permissionRepository.findAllById(permissionNames);

        boolean changed = role.getPermissions().addAll(permissions);

        if (changed) {
            roleRepository.save(role);

            log.info("Assigned {} permissions to role {}", permissions.size(), roleName);
        }
    }
}