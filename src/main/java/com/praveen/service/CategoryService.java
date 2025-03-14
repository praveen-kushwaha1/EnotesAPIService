package com.praveen.service;

import java.util.List;

import com.praveen.dto.CategoryDto;
import com.praveen.dto.CategoryResponse;

/**
 * Service interface for managing categories.
 * Defines the contract for business logic operations related to categories.
 */
public interface CategoryService {

    /**
     * Save a category. If the category ID is null, it creates a new category;
     * otherwise, it updates the existing category.
     *
     * @param categoryDto the data transfer object (DTO) containing category details.
     * @return true if the category is saved or updated successfully; false otherwise.
     */
    Boolean saveCategory(CategoryDto categoryDto);

    /**
     * Retrieve all categories.
     *
     * @return a list of {@link CategoryDto} representing all categories.
     */
    List<CategoryDto> getAllCategory();

    /**
     * Retrieve all active categories.
     *
     * @return a list of {@link CategoryResponse} representing active categories.
     * Active categories are those that are not deleted and marked as active.
     */
    List<CategoryResponse> getActiveCategory();

    /**
     * Retrieve category details by ID.
     *
     * @param id the unique identifier of the category.
     * @return a {@link CategoryDto} containing category details if found;
     * null if no such category exists or it is marked as deleted.
     */
    CategoryDto getCategoryById(Integer id);

    /**
     * Mark a category as deleted by its ID.
     * Performs a soft delete by setting the 'isDeleted' field to true.
     *
     * @param id the unique identifier of the category to delete.
     * @return true if the category is successfully marked as deleted; false otherwise.
     */
    Boolean deleteCategory(Integer id);
}
