package com.github.hib.repository;

import com.github.hib.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <CategoryEntity, Integer> {
}
