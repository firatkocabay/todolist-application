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
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "USER_NAME")
	@NotNull(message = "Username is not nullable.")
	private String userName;
	
	@Column(name = "EMAIL")
	@NotNull(message = "Email address is not nullable.")	
	private String email;
	 
	@Column(name = "PASSWORD")
	@NotNull(message = "Password is not nullable.")	
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_TODO_LIST", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "TODO_ID"))
	private Set<ToDoList> toDoList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<ToDoList> getToDoList() {
		return toDoList;
	}

	public void setToDoList(Set<ToDoList> toDoList) {
		this.toDoList = toDoList;
	}
	
}
