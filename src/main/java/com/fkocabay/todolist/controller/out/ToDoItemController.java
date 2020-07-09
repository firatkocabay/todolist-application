package com.fkocabay.todolist.controller.out;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fkocabay.todolist.entity.ToDoItem;
import com.fkocabay.todolist.service.ToDoItemService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api")
public class ToDoItemController {

	Logger logger = LoggerFactory.getLogger(ToDoItemController.class);

	@Autowired
	private ToDoItemService toDoItemService;

	@GetMapping("/todo-item")
	public List<ToDoItem> getToDoItem() {
		return toDoItemService.getAllToDoItems();
	}


	@GetMapping("/todo-item/{id}")
	public ResponseEntity<ToDoItem> getToDoItemById(@PathVariable Long id) throws NotFoundException {
		Optional<ToDoItem> toDoItem = toDoItemService.findById(id);
		if (!toDoItem.isPresent())
			ResponseEntity.noContent().build();
		//			throw new NotFoundException("This To-Do List not found!");

		return ResponseEntity.ok(toDoItem.get());
	}

	@PostMapping("/todo-item/create")
	public ResponseEntity<ToDoItem> createToDoItem(@Valid @RequestBody ToDoItem toDoItem) throws Exception {
		logger.info("" + toDoItem.getOwnerListId());
		final ToDoItem createdToDoItem = toDoItemService.save(toDoItem);
		return new ResponseEntity<ToDoItem>(createdToDoItem, HttpStatus.CREATED);
	}

	@PutMapping("/todo-item/update/{id}")
	public ResponseEntity<ToDoItem> updateToDoItem(@RequestBody ToDoItem toDoItem, @PathVariable Long id) throws Exception {
		Optional<ToDoItem> optionalToDoItem = toDoItemService.findById(id);

		if (!optionalToDoItem.isPresent())
			ResponseEntity.noContent().build();
		//			throw new NotFoundException("This To-Do List not found!");

		toDoItem.setId(id);
		toDoItemService.update(toDoItem);
		return ResponseEntity.ok(toDoItem);
	}

	@DeleteMapping("/todo-item/delete/{id}")
	public void deleteToDoItem(@PathVariable Long id) {
		toDoItemService.deleteById(id);
	}

}
