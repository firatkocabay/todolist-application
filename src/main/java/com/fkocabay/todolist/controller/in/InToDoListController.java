package com.fkocabay.todolist.controller.in;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fkocabay.todolist.entity.ToDoItem;
import com.fkocabay.todolist.entity.ToDoList;
import com.fkocabay.todolist.entity.User;
import com.fkocabay.todolist.service.ToDoItemService;
import com.fkocabay.todolist.service.ToDoListService;
import com.fkocabay.todolist.service.UserService;

@Controller
public class InToDoListController {
	
	Logger logger = LoggerFactory.getLogger(InToDoListController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private ToDoListService toDoListService;
	
	@Autowired
	private ToDoItemService toDoItemService;

	private User getAuthUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		return user;
	}

	@RequestMapping(value = {"/home/todo-list"}, method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView model = new ModelAndView();
		final User user = getAuthUser();

		model.addObject("userName", user.getUserName());
		Optional<List<ToDoList>> toDoList = Optional.ofNullable(toDoListService.findAllToDoList(user));
		if (toDoList.isPresent()) {
			model.addObject("toDoList", toDoList.get());
		}
		model.setViewName("home/todo-list");
		return model;
	}

	@RequestMapping(value = {"/home/todo-list"}, method = RequestMethod.POST)
	public String createToDoList(@Valid ToDoList toDoList, BindingResult bindingResult) throws Exception {

//		ToDoList toDoList = new ToDoList();
//		toDoList.setName(name);
		
		ModelAndView model = new ModelAndView();
		final User user = getAuthUser();

		if (toDoList.getId() != null) {
			Optional<ToDoList> toDoListExists = toDoListService.findById(toDoList.getId());

			if(toDoListExists.isPresent()) {
				bindingResult.rejectValue("name", "error.todolist", "This to-do list already exists!");
			}
		}

		if(bindingResult.hasErrors()) {
			model.setViewName("home/todo-list");
		} else {
			toDoList.setOwnerUserId(user.getId());
			final ToDoList finaltoDoList = toDoListService.save(toDoList);
			model.addObject("msg", "To-do list created successfully!");
			model.addObject("toDoList", finaltoDoList);
			model.setViewName("home/todo-list");
		}

		return "redirect:/home/todo-list";
	}
	
	@PostMapping("/todo-item/create")
	public String createToDoItem(@Valid ToDoItem toDoItem, BindingResult bindingResult) throws Exception {
		ModelAndView model = new ModelAndView();

		logger.info("" + toDoItem.getOwnerListId());
		
		if(bindingResult.hasErrors()) {
			model.setViewName("home/todo-list");
		} else {
			final ToDoItem createdToDoItem = toDoItemService.save(toDoItem);
			model.addObject("msg", "To-do item created successfully!");
			model.addObject("toDoItem", createdToDoItem);
			model.setViewName("home/todo-list");
		}

		return "redirect:/home/todo-list";
	}

	@RequestMapping(value = {"/todo-list/update"}, method = RequestMethod.PUT)
	public ModelAndView updateToDoList(@Valid ToDoList toDoList, BindingResult bindingResult) throws Exception {

		ModelAndView model = new ModelAndView();
		final User user = getAuthUser();

		Optional<ToDoList> toDoListExists = toDoListService.findById(toDoList.getId());

		if(!toDoListExists.isPresent()) {
			toDoListService.save(toDoList);
		}

		if(bindingResult.hasErrors()) {
			model.setViewName("home/todo-list");
		} else {
			toDoListExists.ifPresent(todo -> {
				todo.setName(toDoList.getName());
				todo.setOwnerUserId(user.getId());
				try {
					toDoListService.save(todo);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			});
			model.addObject("msg", "To-do list updated successfully!");
			model.addObject("toDoList", toDoList);
			model.setViewName("home/todo-list");
		}

		return model;
	}

	@RequestMapping(value = {"/todo-list/delete/{id}"}, method = RequestMethod.DELETE)
	public String deleteToDoList(@PathVariable Long id) {
		toDoListService.deleteById(id);
		return "redirect:/home/todo-list";
	}

	@RequestMapping(value = {"/todo-item/delete/{id}"}, method = RequestMethod.DELETE)
	public void deleteToDoItem(@PathVariable Long id) {
		ModelAndView model = new ModelAndView();
		toDoItemService.deleteById(id);
		model.setViewName("home/todo-list");
	}
}
