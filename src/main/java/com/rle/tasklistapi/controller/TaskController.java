package com.rle.tasklistapi.controller;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rle.tasklistapi.entidade.Task;
import com.rle.tasklistapi.enums.Status;
import com.rle.tasklistapi.repository.TaskRepository;

@RestController
@RequestMapping({"/tasks"})
@CrossOrigin(origins = "*")
public class TaskController {

	private TaskRepository repository;
	
	TaskController(TaskRepository taskRepository){
		this.repository = taskRepository;
	}
	
	@GetMapping(path = {"/hello"})
	public String hello(){
		return "Hello World";
	}
	
	@GetMapping
	public List<Task> findAll(){
	   return this.repository.findAll();
	}
	
	@GetMapping(path = {"/{page}/{count}"})
	public Page<Task> findAllPage(@PathVariable int page, @PathVariable int count){		
		Pageable pages = PageRequest.of(page, count);
		return this.repository.findAll(pages);
	}
	
	@GetMapping(path = {"/{id}"})
	public ResponseEntity<Task> findById(@PathVariable long id){
	   return repository.findById(id)
	           .map(record -> ResponseEntity.ok().body(record))
	           .orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public Task create(@RequestBody Task task){
		task.setStatus(Status.ABERTO.getDescricao());
		task.setDtCriacao(new Date());
		task.setDtAlteracao(new Date());
		return repository.save(task);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Task> update(@PathVariable("id") long id, @RequestBody Task task) {
	   return repository.findById(id)
	           .map(record -> {
	               record.setTitulo(task.getTitulo());
	               record.setDescricao(task.getDescricao());
	               record.setStatus(task.getStatus());
	               record.setDtAlteracao(new Date());
	               Task updated = repository.save(record);
	               return ResponseEntity.ok().body(updated);
	           }).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return repository.findById(id)
	           .map(record -> {
	               repository.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
}
