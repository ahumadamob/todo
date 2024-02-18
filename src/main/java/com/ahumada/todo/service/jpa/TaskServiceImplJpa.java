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
		List<Task> tasks = repo.findByDueDateNotNullOrderByDueDate();
		tasks.addAll(repo.findByDueDateNull());
		return tasks;
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

	@Override
	public List<Task> findByDone(boolean done) {
		if(done) {
			List<Task> tasks = repo.findByDueDateNotNullAndDoneTrueOrderByDueDate();
			tasks.addAll(repo.findByDueDateNullAndDoneTrue());
			return tasks;
		}else {
			List<Task> tasks = repo.findByDueDateNotNullAndDoneFalseOrderByDueDate();
			tasks.addAll(repo.findByDueDateNullAndDoneFalse());
			return tasks;			
		}
	}

}
