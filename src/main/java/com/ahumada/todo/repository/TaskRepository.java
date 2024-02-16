package com.ahumada.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahumada.todo.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
