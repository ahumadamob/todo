package com.ahumada.todo.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahumada.todo.entity.Category;
import com.ahumada.todo.repository.CategoryRepository;
import com.ahumada.todo.service.ICategoryService;

@Service
public class CategoryServiceImplJpa implements ICategoryService {
	
	@Autowired
	private CategoryRepository repo;

	@Override
	public List<Category> getAll() {
		return repo.findAll();
	}

	@Override
	public Category getById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Category save(Category category) {
		return repo.save(category);
	}

	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}

	@Override
	public boolean exists(Long id) {
		return id == null ? false : repo.existsById(id);
	}

	@Override
	public List<Category> getAllEnabled() {
		return repo.findByEnabledTrue();
	}

	@Override
	public List<Category> getAllDisabled() {
		return repo.findByEnabledFalse();
	}

}
