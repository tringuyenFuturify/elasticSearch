package com.example.elasticsearch.controller;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.elasticsearch.model.User;
import com.example.elasticsearch.req.BaseReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class is to demo how ElasticsearchTemplate can be used to Save/Retrieve
 */

@RestController
@RequestMapping("/rest/users")
public class UserController {

	@Autowired
	Client client;

	@PostMapping("/create")
	public String create(@RequestBody User user) throws IOException {

		/*
		 * IndexResponse response = client.prepareIndex("users", "employee",
		 * user.getUserId()) .setSource(jsonBuilder().startObject().field("name",
		 * user.getName()) .field("userSettings",
		 * user.getUserSettings()).field("creationDate", user.getCreationDate())
		 * .endObject()) .get();
		 */
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json = gson.toJson(user);
		IndexResponse response = client.prepareIndex("users", "employee", user.getUserId()).setSource(json).get();
		System.out.println("response id:" + response.getId());
		return response.getResult().toString();
	}

	@GetMapping("/view")
	public Map<String, Object> view() {
		// GetResponse getResponse = client.prepareGet("users", "employee", id).get();
		// System.out.println(getResponse.getSource());

		// return getResponse.getSource();
		Map<String, Object> res = new LinkedHashMap<>();
		String key = "data";
		SearchResponse result = client.prepareSearch("users").setTypes("employee").get();
		List<SearchHit> l = Arrays.asList(result.getHits().getHits());
		l.stream().forEach(a -> {
			@SuppressWarnings("unchecked")
			List<User> lData = (List<User>) res.get(key);
			if (lData == null) {
				lData = new ArrayList<>();
			}

			ObjectMapper mapper = new ObjectMapper();
			User m = mapper.convertValue(a.getSource(), User.class);

			lData.add(m);
			res.put(key, lData);
			res.put(key + "" + a.id(), a.id());
		});

		return res;
	}

	@PostMapping("/view")
	public Map<String, Object> find(@RequestBody final BaseReq req) {
		// GetResponse getResponse = client.prepareGet("users", "employee", id).get();
		// System.out.println(getResponse.getSource());

		// return getResponse.getSource();
		Map<String, Object> res = new LinkedHashMap<>();
		String key = "data";
		SearchResponse result = client.prepareSearch("users").setTypes("employee").get();
		List<SearchHit> l = Arrays.asList(result.getHits().getHits());
		l.stream().forEach(i -> {
			ObjectMapper mapper = new ObjectMapper();
			User m = mapper.convertValue(i.getSource(), User.class);
			if(m.getName().contains(req.getKeyword())) {
				res.put(key + i.getId(), m);
			}
		});

		return res;
	}

	@GetMapping("/view/name/{field}")
	public Map<String, Object> searchByName(@PathVariable final String field) {
		Map<String, Object> map = null;
		SearchResponse response = client.prepareSearch("users").setTypes("employee")
				.setSearchType(SearchType.QUERY_AND_FETCH).setQuery(QueryBuilders.matchQuery("name", field)).get();

		List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
		map = searchHits.get(0).getSource();
		
		return map;

	}

	@GetMapping("/update/{id}")
	public String update(@PathVariable final String id) throws IOException {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index("users").type("employee").id(id)
				.doc(jsonBuilder().startObject().field("name", "Rajesh").endObject());
		try {
			UpdateResponse updateResponse = client.update(updateRequest).get();
			System.out.println(updateResponse.status());
			return updateResponse.status().toString();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e);
		}
		return "Exception";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable final String id) {

		DeleteResponse deleteResponse = client.prepareDelete("users", "employee", id).get();

		System.out.println(deleteResponse.getResult().toString());
		return deleteResponse.getResult().toString();
	}
}