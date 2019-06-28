package com.example.aws.elasticsearch.demo.document;

import lombok.Data;

@Data
public class ProfileDocument {

	private String id;
	private String name;

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

	public ProfileDocument(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public ProfileDocument() {
		super();
	}

}