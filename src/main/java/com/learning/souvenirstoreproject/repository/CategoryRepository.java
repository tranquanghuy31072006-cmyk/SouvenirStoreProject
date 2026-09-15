package com.learning.souvenirstoreproject.repository;

import com.learning.souvenirstoreproject.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findAllByStatus(String status);

    boolean existsByName(@NotBlank @Size(max = 100) String name);
}
