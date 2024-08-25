package com.ahumada.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahumada.todo.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	// select * from project where description like '%mitexto%'
	public List<Project> findByDescriptionLike(String description);
}
