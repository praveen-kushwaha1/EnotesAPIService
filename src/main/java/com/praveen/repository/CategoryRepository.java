package com.praveen.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.praveen.entity.Category;

/**
 * Repository interface for the {@link Category} entity.
 * Extends the {@link JpaRepository} to provide basic CRUD and custom query operations.
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * Find all categories that are marked as active.
     *
     * @return a list of categories where 'isActive' is true.
     */
    List<Category> findByIsActiveTrue();

    /**
     * Find all categories that are active and not marked as deleted.
     *
     * @return a list of categories where 'isActive' is true and 'isDeleted' is false.
     */
    List<Category> findByIsActiveTrueAndIsDeletedFalse();

    /**
     * Find a category by its ID, ensuring that it is not marked as deleted.
     *
     * @param id the unique identifier of the category.
     * @return an {@link Optional} containing the category if found and not deleted.
     */
    Optional<Category> findByIdAndIsDeletedFalse(Integer id);

    /**
     * Find all categories that are not marked as deleted.
     *
     * @return a list of categories where 'isDeleted' is false.
     */
    List<Category> findByIsDeletedFalse();


    Boolean existsByName(String name);
}
