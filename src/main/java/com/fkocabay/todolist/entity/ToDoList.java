package com.fkocabay.todolist.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TODO_LIST")
public class ToDoList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TODO_ID")
	private Long id;
	
	@Column(name = "NAME")
	@NotNull(message = "List name is not nullable.")
	private String name;
	
	@Column(name = "OWNER_USER_ID")
	private Long ownerUserId;
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TODO_LIST_ITEM", joinColumns = @JoinColumn(name = "TODO_ID"), inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
	private Set<ToDoItem> toDoItems;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(Long ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public Set<ToDoItem> getToDoItems() {
		return toDoItems;
	}

	public void setToDoItems(Set<ToDoItem> toDoItems) {
		this.toDoItems = toDoItems;
	}
	
	
}
