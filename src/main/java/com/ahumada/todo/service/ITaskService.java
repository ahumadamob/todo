package com.ahumada.todo.service;

import java.util.List;

import com.ahumada.todo.entity.Task;

public interface ITaskService {
	public List<Task> findAll();
	public Task findById(Long id);
	public Task save(Task task);
	public void delete(Long id);
	public boolean exists(Long id);
}
