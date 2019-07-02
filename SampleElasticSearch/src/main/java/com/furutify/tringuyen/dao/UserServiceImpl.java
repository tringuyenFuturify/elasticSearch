package com.furutify.tringuyen.dao;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import com.furutify.tringuyen.model.User;

@Repository
public class UserServiceImpl implements UserService {

	@Value("${elasticsearch.index.name}")
	private String indexName;

	@Value("${elasticsearch.user.type}")
	private String userTypeName;

	@Autowired
	private ElasticsearchTemplate esTemplate;

	@Override
	public List<User> getAllUsers() {
		SearchQuery getAllQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
		return esTemplate.queryForList(getAllQuery, User.class);
	}

	@Override
	public User addNewUser(User user) {
		IndexQuery userQuery = new IndexQuery();
		userQuery.setIndexName(indexName);
		userQuery.setType(userTypeName);
		userQuery.setObject(user);

		esTemplate.refresh(indexName);

		return user;
	}

}