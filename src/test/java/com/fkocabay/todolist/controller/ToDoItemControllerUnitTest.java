package com.fkocabay.todolist.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

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
import com.fkocabay.todolist.controller.out.ToDoItemController;
import com.fkocabay.todolist.entity.ToDoItem;
import com.fkocabay.todolist.service.ToDoItemService;

@RunWith(SpringRunner.class)
@WebMvcTest(ToDoItemController.class)
@AutoConfigureTestDatabase
public class ToDoItemControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ToDoItemService toDoItemService;	

	@WithMockUser(value = "spring")
	@Test
	public void getAllToDoItem() throws Exception {
		mockMvc.perform(get("/api/todo-item").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().json("[]"));

		verify(toDoItemService, times(1)).getAllToDoItems();
	}

	
		@Test
		public void createAToDoItem() throws Exception {
			final ToDoItem toDoItem = new ToDoItem();
			toDoItem.setId(1L);
			toDoItem.setOwnerListId(1L);
			toDoItem.setName("Test Item");
			toDoItem.setIsDone(Boolean.FALSE);
			toDoItem.setDescription("Test To Do Item");
			toDoItem.setDeadline(new Date());
	
			when(toDoItemService.save(any(ToDoItem.class))).thenReturn(toDoItem);
	
			mockMvc.perform(post("/api/todo-item/create")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(toDoItem)))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated());
		}
		
		@Test
		public void deleteAToDoItem() throws Exception {
	
			mockMvc.perform(delete("/api/todo-item/delete/1")
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
