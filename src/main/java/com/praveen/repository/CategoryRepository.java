package com.praveen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.praveen.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	List<Category> findByIsActiveTrue();

}