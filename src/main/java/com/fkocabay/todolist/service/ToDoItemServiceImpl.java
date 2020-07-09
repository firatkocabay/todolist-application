package com.fkocabay.todolist.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fkocabay.todolist.entity.ToDoItem;
import com.fkocabay.todolist.entity.ToDoList;
import com.fkocabay.todolist.repository.ToDoItemRepository;
import com.fkocabay.todolist.repository.ToDoListRepository;

import javassist.NotFoundException;

@Service
public class ToDoItemServiceImpl implements ToDoItemService{
	
	@Autowired
	private ToDoItemRepository toDoItemRepository;
	
	@Autowired
	private ToDoListRepository toDoListRepository;

	@Override
	public List<ToDoItem> getAllToDoItems() {
		return toDoItemRepository.findAll();
	}

	@Override
	public Optional<ToDoItem> findById(Long id) {
		return toDoItemRepository.findById(id);
	}

	@Override
	@Transactional
	public ToDoItem save(ToDoItem toDoItem) throws Exception {
		final Date createDate = new Date();
		toDoItem.setCreateDate(createDate);
		
		Optional<ToDoList> ownerToDoList = toDoListRepository.findById(toDoItem.getOwnerListId());
		
		if(ownerToDoList.isPresent()) {
			ownerToDoList.get().getToDoItems().add(toDoItem);
		} else {
			throw new NotFoundException("Owner ToDo List is not found!");
		}
		
		return toDoItemRepository.save(toDoItem);
	}

	@Override
	@Transactional
	public ToDoItem update(ToDoItem toDoItem) {
		return toDoItemRepository.save(toDoItem);
	}
	
	@Override
	public void deleteById(Long id) {
		toDoItemRepository.deleteById(id);
	}


}
