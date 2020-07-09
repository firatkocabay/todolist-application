package com.fkocabay.todolist.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fkocabay.todolist.controller.out.ToDoListController;
import com.fkocabay.todolist.entity.ToDoList;
import com.fkocabay.todolist.service.ToDoListService;

@RunWith(SpringRunner.class)
@WebMvcTest(ToDoListController.class)
@AutoConfigureTestDatabase
public class ToDoListControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ToDoListService toDoListService;	

	@WithMockUser(value = "spring")
	@Test
	public void getAllToDoList() throws Exception {
		mockMvc.perform(get("/api/todo-list").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().json("[]"));

		verify(toDoListService, times(1)).getAllToDoList();
	}

	
		@Test
		public void createAToDoList() throws Exception {
			final ToDoList toDoList = new ToDoList();
			toDoList.setId(1L);
			toDoList.setOwnerUserId(1L);
			toDoList.setName("Test To Do List");
	
			when(toDoListService.save(any(ToDoList.class))).thenReturn(toDoList);
	
			mockMvc.perform(post("/api/todo-list/create")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(toDoList)))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated());
		}

		@Test
		public void deleteAToDoList() throws Exception {
	
			mockMvc.perform(delete("/api/todo-list/delete/1")
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		}
		
		public static String asJsonString(final Object obj) {
			try {
				return new ObjectMapper().writeValueAsString(obj);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

}
