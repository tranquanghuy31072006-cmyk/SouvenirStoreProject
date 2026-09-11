package com.learning.mywebsiteproject.repository;

import com.learning.mywebsiteproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<User,String> {
}
