package io.futurify.authentication.service;

import io.futurify.authentication.model.User;

public interface UserService {

  public User signIn(String username, String password);

}
