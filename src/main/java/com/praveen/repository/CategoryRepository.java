package com.praveen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.praveen.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}