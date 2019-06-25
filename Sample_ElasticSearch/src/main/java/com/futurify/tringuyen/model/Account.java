package com.futurify.tringuyen.model;

import static com.futurify.tringuyen.util.Constant.INDEX;
import static com.futurify.tringuyen.util.Constant.TYPE_ACCOUNT;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = INDEX, type = TYPE_ACCOUNT)
public class Account {

	@Id
	private String id;

	private String name;

	private Integer age;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Account(String id, String name, Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public Account() {
		super();
	}

}