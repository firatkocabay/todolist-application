package com.fkocabay.todolist.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TODO_ITEM")
public class ToDoItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ITEM_ID")
	private Long id;
	
	@Column(name = "NAME")
	@NotNull(message = "Item name is not nullable.")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "IS_DONE")
	private Boolean isDone = false;
	
	@Column(name = "OWNER_LIST_ID")
	@NotNull(message = "Owner list id is not nullable.")
	private Long ownerListId;
	
	@Column(name = "CREATE_DATE")
	private Date createDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DEADLINE")
	@NotNull(message = "Deadline is not nullable.")
	private Date deadline;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}

	public Long getOwnerListId() {
		return ownerListId;
	}

	public void setOwnerListId(Long ownerListId) {
		this.ownerListId = ownerListId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	
}
