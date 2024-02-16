package com.ahumada.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahumada.todo.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
