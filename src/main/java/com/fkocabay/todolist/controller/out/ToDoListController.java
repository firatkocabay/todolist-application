package com.fkocabay.todolist.controller.out;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fkocabay.todolist.entity.ToDoList;
import com.fkocabay.todolist.service.ToDoListService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/api")
public class ToDoListController {
	
	Logger logger = LoggerFactory.getLogger(ToDoListController.class);

	
	private ToDoListService toDoListService;
	
	@Autowired
	public void setToDoListService(ToDoListService toDoListService) {
		this.toDoListService = toDoListService;
	}
	
	@GetMapping("/todo-list")
	public ResponseEntity<List<ToDoList>> getToDoList() {
		List<ToDoList> toDoList = toDoListService.getAllToDoList();
		return new ResponseEntity<List<ToDoList>>(toDoList, HttpStatus.OK);
	}

	@GetMapping("/todo-list/{id}")
	public ResponseEntity<ToDoList> getToDoListById(@PathVariable Long id) throws NotFoundException {
		Optional<ToDoList> toDoList = toDoListService.findById(id);
		if (!toDoList.isPresent())
			ResponseEntity.noContent().build();
//			throw new NotFoundException("This To-Do List not found!");
				
		return ResponseEntity.ok(toDoList.get());
	}
		
	@PostMapping(path = "/todo-list/create", produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "/todo-list/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
	public ResponseEntity<ToDoList> createToDoList(@Valid @RequestBody ToDoList toDoList) throws Exception {
		ToDoList createdToDoList = toDoListService.save(toDoList);
		return new ResponseEntity<ToDoList>(createdToDoList, HttpStatus.CREATED);
	}
	
	@PutMapping("/todo-list/update/{id}")
	public ResponseEntity<ToDoList> updateToDoList(@RequestBody ToDoList toDoList, @PathVariable Long id) throws Exception {
		Optional<ToDoList> optionalToDoList = toDoListService.findById(id);
		
		if (!optionalToDoList.isPresent())
			ResponseEntity.noContent().build();
//			throw new NotFoundException("This To-Do List not found!");
		
		toDoList.setId(id);
		toDoListService.save(toDoList);
		return ResponseEntity.ok(toDoList);
	}

	@DeleteMapping("/todo-list/delete/{id}")
	public void deleteToDoList(@PathVariable Long id) {
		toDoListService.deleteById(id);
	}
	
}
