package com.fkocabay.todolist.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fkocabay.todolist.controller.out.UserController;
import com.fkocabay.todolist.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureTestDatabase
public class UserControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserService userService;	

	@WithMockUser(value = "spring")
	@Test
	public void getAllToDoItem() throws Exception {
		mockMvc.perform(get("/api/all-user").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().json("[]"));

		verify(userService, times(1)).getAllUsers();
	}

}
