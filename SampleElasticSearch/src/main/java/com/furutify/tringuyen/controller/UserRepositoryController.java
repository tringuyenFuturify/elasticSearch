package com.furutify.tringuyen.controller;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furutify.tringuyen.dao.UserDao;
import com.furutify.tringuyen.model.User;
import com.google.gson.Gson;

@RestController
@RequestMapping("/repo")
public class UserRepositoryController {

	@Autowired
	private UserDao userDao;

	@SuppressWarnings("unchecked")
	public Map<String, Object> callLogInAccountRVezy() throws Exception {
		@SuppressWarnings("deprecation")
		CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
			String url = "https://rvezy-uat-auth.futurify.io";
			String port = "";
			String finalUrl = url + port + "/token";
			// RestTemplate rest = new RestTemplate();
			RestTemplate rest = new RestTemplateBuilder().setConnectTimeout(10 * 1000).setReadTimeout(10 * 1000)
					.build();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			MultiValueMap<String, String> m = new LinkedMultiValueMap<>();
			m.add("UserName", "nhanpham@futurify.vn");
			m.add("Password", "kocopass.123");
			//m.add("Password", "123123");

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(m, headers);
			ResponseEntity<String> response = rest.exchange(finalUrl, HttpMethod.POST, request, String.class);
			String result = response.getBody(); // json as stringify

			Gson gson = new Gson();
			Map<String, Object> res = gson.fromJson(result, Map.class);
			res.put("isSuccess", true);

			return res;
		}).exceptionally((e) -> {
			Map<String, Object> res = new LinkedHashMap<>();

			res.put("isSuccess", false);
			res.put("exception", e);

			return res;
		});

		return future.get();
	}

	@RequestMapping("/all")
	public ResponseEntity<?> getAllUsers() {
		Object res = null;

		try {
			List<User> lData = StreamSupport.stream(userDao.findAll().spliterator(), false)
					.sorted(Comparator.comparing(User::getUserId)/* .reversed() */).collect(Collectors.toList());
			// userDao.findAll().forEach(users::add);
			Map<String, Object> dataResponse = callLogInAccountRVezy();

			Map<String, Object> mData = new LinkedHashMap<>();
			mData.put("data", lData);
			mData.put("dataResponse", dataResponse);

			res = mData;
		} catch (Exception ex) {
			res = ex;
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public User addNewUsers(@RequestBody User user) {
		UUID uuid = UUID.randomUUID();
		user.setUserId(uuid.toString());
		userDao.save(user);

		return user;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public User editNewUsers(@RequestBody User user) {
		User m = userDao.findById(user.getUserId()).orElse(null);

		if (m != null) {
			ObjectMapper mapper = new ObjectMapper();
			m = mapper.convertValue(user, User.class);
			userDao.save(m);
		}

		return m;
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<?> removeNewUsers(@PathVariable String id) {
		User m = userDao.findById(id).orElse(null);

		if (m != null) {
			userDao.delete(m);
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>("Id " + id + " not found", HttpStatus.ACCEPTED);
	}

}