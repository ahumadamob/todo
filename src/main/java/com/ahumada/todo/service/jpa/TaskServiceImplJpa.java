package com.ahumada.todo.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahumada.todo.entity.Task;
import com.ahumada.todo.repository.TaskRepository;
import com.ahumada.todo.service.ITaskService;

@Service
public class TaskServiceImplJpa implements ITaskService {
	
	@Autowired
	private TaskRepository repo;

	@Override
	public List<Task> findAll() {
		return repo.findAll();
	}

	@Override
	public Task findById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Task save(Task task) {
		return repo.save(task);
	}

	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public boolean exists(Long id) {
		return id == null ? false : repo.existsById(id);
	}

}
