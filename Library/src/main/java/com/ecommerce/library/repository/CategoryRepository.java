package com.ecommerce.library.repository;

import com.ecommerce.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long id);

    @Query("select c from Category c where c.name like ?1")
    boolean existsByName(String name);

    @Query("select c from Category c where c.is_activated = true and c.is_deleted = false ")
    List<Category> findAllCategoryActivated();
}
