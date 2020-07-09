package com.fkocabay.todolist.controller.out;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fkocabay.todolist.entity.User;
import com.fkocabay.todolist.service.UserService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/all-user")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}


	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) throws NotFoundException {
		Optional<User> optionalUser = userService.findById(id);
		if (!optionalUser.isPresent())
			ResponseEntity.noContent().build();
		//			throw new NotFoundException("This To-Do List not found!");

		return ResponseEntity.ok(optionalUser.get());
	}

	@PostMapping("/registration")
	public void createToDoList(@Valid @RequestBody User user) {
		userService.saveUser(user);
	}

	@PutMapping("/user/update/{id}")
	public ResponseEntity<User> updateToDoList(@RequestBody User user, @PathVariable Long id) throws NotFoundException {
		Optional<User> optionalUser = userService.findById(id);

		if (!optionalUser.isPresent())
			ResponseEntity.noContent().build();
		//			throw new NotFoundException("This To-Do List not found!");

		user.setId(id);
		userService.saveUser(user);
		return ResponseEntity.ok(user);
	}

	@DeleteMapping("/user/delete/{id}")
	public void deleteToDoList(@PathVariable Long id) {
		userService.deleteById(id);
	}

}
