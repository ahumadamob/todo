package com.ahumada.todo.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahumada.todo.entity.Project;
import com.ahumada.todo.repository.ProjectRepository;
import com.ahumada.todo.service.IProjectService;

@Service
public class ProjectServiceImplJpa implements IProjectService {

	@Autowired
	private ProjectRepository repo;
	
	@Override
	public List<Project> findAll() {
		return repo.findAll();
	}

	@Override
	public Project findById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public boolean exists(Long id) {
		return id == null ? false : repo.existsById(id);
	}

	@Override
	public Project save(Project project) {
		return repo.save(project);
	}

	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public List<Project> encontrarPorDescripcion(String descripcion) {
		return repo.findByDescriptionLike(descripcion);
	}

}
