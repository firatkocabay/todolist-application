package com.fkocabay.todolist.service;

import java.util.List;
import java.util.Optional;

import com.fkocabay.todolist.entity.ToDoItem;

public interface ToDoItemService {

	public List<ToDoItem> getAllToDoItems();
	
	public Optional<ToDoItem> findById(Long id);
	
	public ToDoItem	save(ToDoItem toDoItem) throws Exception;
	
	public ToDoItem	update(ToDoItem toDoItem);
	
	public void deleteById(Long id);

}
