package com.fkocabay.todolist.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fkocabay.todolist.entity.ToDoList;
import com.fkocabay.todolist.entity.User;
import com.fkocabay.todolist.repository.ToDoListRepository;
import com.fkocabay.todolist.repository.UserRepository;

import javassist.NotFoundException;

@Service
public class ToDoListServiceImpl implements ToDoListService {
 
	@Autowired
	private ToDoListRepository toDoListRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<ToDoList> getAllToDoList() {
		return toDoListRepository.findAll();
	}
	
	@Override
	public List<ToDoList> findAllToDoList(User user) {
		return toDoListRepository.findAll().stream().filter(list -> list.getOwnerUserId().equals(user.getId())).collect(Collectors.toList());
	}

	@Override
	public Optional<ToDoList> findById(Long id) {
		return toDoListRepository.findById(id);
	}

	@Override
	@Transactional
	public ToDoList save(ToDoList toDoList) throws Exception {

		Optional<User> optionalUser = userRepository.findById(toDoList.getOwnerUserId());
		
		if(optionalUser.isPresent()) {
			optionalUser.get().getToDoList().add(toDoList);
		} else {
			throw new NotFoundException("Owner User is not found!");
		}
		
		return toDoListRepository.save(toDoList);
	}

	@Override
	public void deleteById(Long id) {
		toDoListRepository.deleteById(id);
	}

}