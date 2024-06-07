package com.ahumada.todo.service;

import com.ahumada.todo.entity.Category;
import java.util.List;

public interface ICategoryService {
	
	public List<Category> getAll();
	public Category getById(Long id);
	public Category save(Category category);
	public void deleteById(Long id);
	public boolean exists(Long id);
	public List<Category>getAllEnabled();
	public List<Category>getAllDisabled();

}
