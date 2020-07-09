package com.fkocabay.todolist.service;

import java.util.List;
import java.util.Optional;

import com.fkocabay.todolist.entity.User;

public interface UserService {
	
	public User findUserByEmail(String email);
	
	public void saveUser(User user);
	
	public List<User> getAllUsers();
	
	public Optional<User> findById(Long id);
	
	public void deleteById(Long id);

}
