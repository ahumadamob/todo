package com.ahumada.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahumada.todo.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	public List<Task> findByDoneTrue();
	public List<Task> findByDoneFalse();
}
