package com.fkocabay.todolist.service;

import java.util.List;
import java.util.Optional;

import com.fkocabay.todolist.entity.ToDoList;
import com.fkocabay.todolist.entity.User;

public interface ToDoListService {

	public List<ToDoList> getAllToDoList();
	
	public List<ToDoList> findAllToDoList(User user);
	
	public Optional<ToDoList> findById(Long id);
	
	public ToDoList save(ToDoList toDoList) throws Exception;
	
	public void deleteById(Long id);

}
