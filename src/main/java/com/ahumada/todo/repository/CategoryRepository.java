package com.ahumada.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahumada.todo.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	public List<Category>findByEnabledTrue();
	public List<Category>findByEnabledFalse();
}
