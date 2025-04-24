package com.praveen.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.praveen.exception.ExistDataException;
import com.praveen.exception.ResourceNotFoundException;
import com.praveen.validation.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.praveen.dto.CategoryDto;
import com.praveen.dto.CategoryResponse;
import com.praveen.entity.Category;
import com.praveen.repository.CategoryRepository;
import com.praveen.service.CategoryService;

/**
 * Implementation of the {@link CategoryService} interface.
 * This class contains business logic for handling operations related to categories.
 * It provides methods to create, update, retrieve, and delete categories.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;
    private final ModelMapper modelMapper;
    private final Validation validation;

    /**
     * Constructor-based dependency injection for CategoryServiceImpl.
     *
     * @param categoryRepo the repository instance to handle database operations.
     * @param modelMapper  the ModelMapper instance to map entities to DTOs and vice versa.
     */
    public CategoryServiceImpl(CategoryRepository categoryRepo, ModelMapper modelMapper,Validation validation) {
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
        this.validation=validation;
    }

    /**
     * Saves a category. If the category has an ID, it updates the existing category.
     * Otherwise, it creates a new category.
     *
     * @param categoryDto the category data to save.
     * @return true if the operation was successful, false otherwise.
     */
    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {
        validation.categoryValidation(categoryDto);
        // check category exist or not
        Boolean exist = categoryRepo.existsByName(categoryDto.getName().trim());
        if (exist) {
            // throw error
            throw new ExistDataException("Category already exist");
        }
        // Map CategoryDto to Category entity
        Category category = modelMapper.map(categoryDto, Category.class);

        // Check if it's a new category or an update
        if (category.getId() == null) {
            // Populate createdOn and createdBy for new categories
            category.setIsDeleted(false);
           // category.setCreatedBy(1); // Example value for 'createdBy'
            category.setCreatedOn(new Date());
        } else {
            // Update existing category details
            updateCategory(category);
        }

        // Save the category and return the result
        return categoryRepo.save(category) != null;
    }

    /**
     * Updates existing category details. Copies non-editable fields from the existing record.
     *
     * @param category the updated category data.
     */
    private void updateCategory(Category category) {
        categoryRepo.findById(category.getId()).ifPresent(existingCategory -> {
            // Preserve non-editable fields from the existing category
            category.setCreatedBy(existingCategory.getCreatedBy());
            category.setCreatedOn(existingCategory.getCreatedOn());
            category.setIsDeleted(existingCategory.getIsDeleted());

            // Update the 'updatedBy' and 'updatedOn' fields
           // category.setUpdatedBy(1); // Example value for 'updatedBy'
           // category.setUpdatedOn(new Date());
        });
    }

    /**
     * Retrieves all categories.
     *
     * @return a list of all categories mapped to CategoryDto objects.
     */
    @Override
    public List<CategoryDto> getAllCategory() {
        return categoryRepo.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all active categories.
     *
     * @return a list of active categories mapped to CategoryResponse objects.
     */
    @Override
    public List<CategoryResponse> getActiveCategory() {
        return categoryRepo.findByIsActiveTrueAndIsDeletedFalse().stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category to retrieve.
     * @return the category mapped to a CategoryDto, or null if not found or marked as deleted.
     */
    @Override
    public CategoryDto getCategoryById(Integer id) throws Exception {
        return categoryRepo.findByIdAndIsDeletedFalse(id)
                .map(category -> {
                    // Transform the name to uppercase and update the category
                    category.setName(category.getName().toUpperCase());
                    return modelMapper.map(category, CategoryDto.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id=" + id));
    }




    /**
     * Marks a category as deleted by setting its 'isDeleted' field to true.
     *
     * @param id the ID of the category to delete.
     * @return true if the operation was successful, false otherwise.
     */
    @Override
    public Boolean deleteCategory(Integer id) {
        return categoryRepo.findById(id).map(category -> {
            category.setIsDeleted(true); // Soft delete by marking as deleted
            categoryRepo.save(category);
            return true;
        }).orElse(false);
    }
}
