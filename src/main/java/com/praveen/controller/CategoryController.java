package com.praveen.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.praveen.dto.CategoryDto;
import com.praveen.dto.CategoryResponse;
import com.praveen.service.CategoryService;



/**
 * REST controller for managing categories in the application.
 * Provides endpoints for creating, retrieving, updating, and deleting categories.
 */
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService; // Dependency injected via constructor

    /**
     * Constructor-based dependency injection for CategoryService.
     *
     * @param categoryService the CategoryService instance to be injected.
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Create a new category.
     *
     * @param categoryDto the category data to be created.
     * @return ResponseEntity containing a success or failure message with appropriate HTTP status.
     */
    @PostMapping
    public ResponseEntity<String> saveCategory( @RequestBody CategoryDto categoryDto) {
        boolean isSaved = categoryService.saveCategory(categoryDto);
        if (isSaved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Category creation failed.");
    }

    /**
     * Retrieve a list of all categories.
     *
     * @return ResponseEntity containing the list of all categories or an empty response if none are found.
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategory();
        if (CollectionUtils.isEmpty(categories)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categories);
    }

    /**
     * Retrieve a list of active categories.
     *
     * @return ResponseEntity containing the list of active categories or an empty response if none are found.
     */
    @GetMapping("/active")
    public ResponseEntity<List<CategoryResponse>> getActiveCategories() {
        List<CategoryResponse> activeCategories = categoryService.getActiveCategory();
        if (CollectionUtils.isEmpty(activeCategories)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(activeCategories);
    }

    /**
     * Retrieve details of a specific category by its ID.
     *
     * @param id the ID of the category to retrieve.
     * @return ResponseEntity containing the category details or an error message if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        if (categoryDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found with Id=" + id);
        }
        return ResponseEntity.ok(categoryDto);
    }

    /**
     * Delete a category by its ID.
     *
     * @param id the ID of the category to delete.
     * @return ResponseEntity containing a success or failure message with appropriate HTTP status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        boolean isDeleted = categoryService.deleteCategory(id);
        if (isDeleted) {
            return ResponseEntity.ok("Category deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete category.");
    }
}
