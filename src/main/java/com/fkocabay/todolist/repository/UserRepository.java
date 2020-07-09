package com.fkocabay.todolist.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fkocabay.todolist.entity.User;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
	
	public List<User> findAll();

	public User findByEmail(String email);
	
}
