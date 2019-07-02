package com.furutify.tringuyen.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.furutify.tringuyen.model.User;

public interface UserDao extends ElasticsearchRepository<User, String> {

}