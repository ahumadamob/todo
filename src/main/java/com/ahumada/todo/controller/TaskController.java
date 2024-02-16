package com.ahumada.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.ConstraintViolationException;
import com.ahumada.todo.entity.Task;
import com.ahumada.todo.service.ITaskService;
import com.ahumada.todo.util.APIResponse;
import com.ahumada.todo.util.ResponseUtil;

@RestController
@RequestMapping(path="/api/v1/task")
public class TaskController {
	
	@Autowired
	private ITaskService taskService;
	
	@GetMapping
	public ResponseEntity<APIResponse<List<Task>>> getAllTasks() {
		List<Task> tasks = taskService.findAll();
		return 	tasks.isEmpty()? ResponseUtil.notFound("No se encontraron tareas") :
				ResponseUtil.success(tasks);		
	}
	
	@GetMapping("/done")
	public ResponseEntity<APIResponse<List<Task>>> getAllTaskDone() {
		return this._getTasksByDone(true);	
	}
	
	@GetMapping("/undone")
	public ResponseEntity<APIResponse<List<Task>>> getAllTaskUndone() {
		return this._getTasksByDone(false);	
	}	
	
	private ResponseEntity<APIResponse<List<Task>>> _getTasksByDone(boolean done) {
		List<Task> tasks = taskService.findByDone(done);
		return 	tasks.isEmpty()? ResponseUtil.notFound("No se encontraron tareas") :
				ResponseUtil.success(tasks);		
	}	
	
	@GetMapping("{id}")
	public ResponseEntity<APIResponse<Task>> getTaskById(@PathVariable("id") Long id){
		return 	taskService.exists(id)? ResponseUtil.success(taskService.findById(id)):
				ResponseUtil.notFound("No se encontró la tarea con id {0}", id);
	}
	
	@PostMapping
	public ResponseEntity<APIResponse<Task>> createTask(@RequestBody Task task){
		return 	taskService.exists(task.getId())? ResponseUtil.badRequest("Ya existe una tarea con id {0}", task.getId()):
				ResponseUtil.success(taskService.save(task));
	}
	
	@PutMapping
	public ResponseEntity<APIResponse<Task>> updateTask(@RequestBody Task task){
		return 	taskService.exists(task.getId())? ResponseUtil.success(task):
				ResponseUtil.badRequest("No existe una tarea con id {0}", task.getId());
	}
	
	@PatchMapping("/done/{id}")
	private ResponseEntity<APIResponse<Task>> setTaskAsDone(@PathVariable("id") Long id){
		return this._setTaskDone(id, true);
	}
	
	@PatchMapping("/undone/{id}")
	private ResponseEntity<APIResponse<Task>> setTaskAsUndone(@PathVariable("id") Long id){
		return this._setTaskDone(id, false);
	}	
	
	private ResponseEntity<APIResponse<Task>> _setTaskDone(Long id, boolean done){
		if(taskService.exists(id)) {
			Task task = taskService.findById(id);
			task.setDone(done);
			taskService.save(task);
			return ResponseUtil.success(task);
		}else {
			return ResponseUtil.badRequest("No existe una tarea con id {0}", id);
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<APIResponse<Task>> deleteTask(@PathVariable("id") Long id){
		if(taskService.exists(id)) {
			taskService.delete(id);
			return ResponseUtil.successDeleted("Se eliminó la tarea con el id {0}", id);
		}else {
			return ResponseUtil.badRequest("No se encontró la tarea con el id {0}", id);
		}		
	}
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Task>> handleException(Exception ex) {    	
    	return ResponseUtil.badRequest(ex.getMessage());
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<Task>> handleConstraintViolationException(ConstraintViolationException ex) {
    	return ResponseUtil.handleConstraintException(ex);
    } 

}
