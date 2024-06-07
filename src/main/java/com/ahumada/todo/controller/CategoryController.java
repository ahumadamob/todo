package com.ahumada.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahumada.todo.entity.Category;
import com.ahumada.todo.service.ICategoryService;

@RestController
@RequestMapping(path="/api/v1")
public class CategoryController {
	
	@Autowired
	private ICategoryService service;
	
	@GetMapping(path="/category")
	public List<Category> showAllCategories(){
		return service.getAll();
	}
	
// http://localhost:8080/api/v1/enabledcategories
//	http://localhost:8080/api/v1/disabledcategories
	
	@GetMapping(path="disabledcategories")
	public List<Category> showDisabledCategories(){
		return service.getAllDisabled();
	}
	
	@GetMapping(path="enabledcategories")
	public List<Category> showEnabledCategories(){
		return service.getAllEnabled();
	}

}
