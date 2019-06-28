package com.example.elasticsearch.req;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseReq {

	@JsonProperty(value = "keyword")
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public BaseReq(String keyword) {
		super();
		this.keyword = keyword;
	}

	public BaseReq() {
		super();
	}

}