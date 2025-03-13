package com.praveen.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.praveen.dto.CategoryDto;
import com.praveen.dto.CategoryResponse;
import com.praveen.entity.Category;
import com.praveen.repository.CategoryRepository;
import com.praveen.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
//		Category category = new Category();
//		category.setName(categoryDto.getName());
//		category.setDescription(categoryDto.getDescription());
//		category.setIsActive(categoryDto.getIsActive());
		Category category = modelMapper.map(categoryDto, Category.class);
		category.setIsDeleted(false);
		category.setCreatedBy(1);
		category.setCreatedOn(new Date());
		Category saveCategory = categoryRepo.save(category);
		if (ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryRepo.findAll();

		List<CategoryDto> categoryDtoList = categories.stream().map(cat -> modelMapper.map(cat, CategoryDto.class)).toList();

		return categoryDtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {

		List<Category> categories = categoryRepo.findByIsActiveTrue();
		List<CategoryResponse> categoryList = categories.stream().map(cat -> modelMapper.map(cat, CategoryResponse.class))
				.toList();
		return categoryList;
	}

}