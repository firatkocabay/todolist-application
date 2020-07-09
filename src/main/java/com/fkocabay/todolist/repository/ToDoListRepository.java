package com.fkocabay.todolist.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fkocabay.todolist.entity.ToDoList;

@Repository("toDoListRepository")
public interface ToDoListRepository extends CrudRepository<ToDoList, Long> {
	
	public List<ToDoList> findAll();
	
}
