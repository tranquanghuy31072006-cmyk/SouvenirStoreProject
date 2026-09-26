package com.learning.souvenirstoreproject.repository;

import com.learning.souvenirstoreproject.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
    boolean existsInvalidatedTokenById(String id);
}
