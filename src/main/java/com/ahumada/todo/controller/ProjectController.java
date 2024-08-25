package com.ahumada.todo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahumada.todo.entity.Project;
import com.ahumada.todo.service.IProjectService;
import com.ahumada.todo.util.APIResponse;
import com.ahumada.todo.util.ResponseUtil;

import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping(path="/api/v1/project")
public class ProjectController {
	
	@Autowired
	private IProjectService projectService;
	
	@GetMapping("/metodo1/{id}")
	public Project getProjectByIdForma1(@PathVariable("id") Long id) {
		return projectService.findById(id);
	}
	
	@GetMapping("/pordescripcion/{desc}")
	public List<Project>buscarProyectosPorDescripcion(@PathVariable("desc") String descripcion){
		return projectService.encontrarPorDescripcion("%" + descripcion + "%");
	}
	
	@GetMapping("/pordescripcion2/{desc}")
	public ResponseEntity<List<Project>> buscarProyectosPorDescripcionMetodo2(@PathVariable("desc") String descripcion){
		List<Project> listado = projectService.encontrarPorDescripcion("%" + descripcion + "%");
		if(listado.isEmpty()) {
			return new ResponseEntity<>(listado, HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(listado, HttpStatus.OK); 
		}		
	}
	
	@GetMapping("/metodo2/{id}")
	public APIResponse<Project> getProjectByIdForma2(@PathVariable("id") Long id){
		Project project = new Project(); 
		if(projectService.exists(id)) {
			project = projectService.findById(id);
			APIResponse<Project> api = new APIResponse<Project>(200, null, project);
			return api;
		}else {
			List<String> lstMessages = new ArrayList<>();
			lstMessages.add("No existe el elemento con el ID indicado");
			APIResponse<Project> api = new APIResponse<Project>(404, lstMessages, null);
			return api;
		}
	}
	
	@GetMapping("/metodo3/{id}")
	public ResponseEntity<APIResponse<Project>> getProjectByIdForma3(@PathVariable("id") Long id){
		Project project = new Project(); 
		if(projectService.exists(id)) {
			project = projectService.findById(id);
			APIResponse<Project> api = new APIResponse<Project>(200, null, project);
			return ResponseEntity.status(200).body(api);
		}else {
			List<String> lstMessages = new ArrayList<>();
			lstMessages.add("No existe el elemento con el ID indicado");
			APIResponse<Project> api = new APIResponse<Project>(404, lstMessages, null);
			return ResponseEntity.status(404).body(api);
		}
	}	
	
	
	
	@GetMapping
	public ResponseEntity<APIResponse<List<Project>>> getAllProjects() {
		List<Project> projects = projectService.findAll();
		return 	projects.isEmpty()? ResponseUtil.notFound("No se encontraron proyectos") :
				ResponseUtil.success(projects);		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<APIResponse<Project>> getProjectById(@PathVariable("id") Long id){
		return 	projectService.exists(id)? ResponseUtil.success(projectService.findById(id)):
				ResponseUtil.notFound("No se encontró el proyecto con id {0}", id);
	}	
	
	@PostMapping
	public ResponseEntity<APIResponse<Project>> createProject(@RequestBody Project project){
		return 	projectService.exists(project.getId())? ResponseUtil.badRequest("Ya existe un proyecto con id {0}", project.getId()):
				ResponseUtil.success(projectService.save(project));
	}
	
	@PutMapping
	public ResponseEntity<APIResponse<Project>> updateProject(@RequestBody Project project){
		return 	projectService.exists(project.getId())? ResponseUtil.success(project):
				ResponseUtil.badRequest("No existe un projecto con id {0}", project.getId());
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<APIResponse<Project>> deleteProject(@PathVariable("id") Long id){
		if(projectService.exists(id)) {
			projectService.delete(id);
			return ResponseUtil.successDeleted("Se eliminó el proyecto con el id {0}", id);
		}else {
			return ResponseUtil.badRequest("No se encontró el proyecto con el id {0}", id);
		}		
	}
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Project>> handleException(Exception ex) {    	
    	return ResponseUtil.badRequest(ex.getMessage());
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<Project>> handleConstraintViolationException(ConstraintViolationException ex) {
    	return ResponseUtil.handleConstraintException(ex);
    } 	

}
