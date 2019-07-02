package com.furutify.tringuyen.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "my_index", type = "user")
public class User {

	@Id
	private String userId = "";

	private String name = "";

	private Date creationDate = new Date();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User(String userId, String name, Date creationDate) {
		super();
		this.userId = userId;
		this.name = name;
		this.creationDate = creationDate;
	}

	public User() {
		super();
	}

}