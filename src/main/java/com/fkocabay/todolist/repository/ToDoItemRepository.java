package com.fkocabay.todolist.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fkocabay.todolist.entity.ToDoItem;

@Repository("toDoItemRepository")
public interface ToDoItemRepository extends CrudRepository<ToDoItem, Long> {
	
	public List<ToDoItem> findAll();
	
}
