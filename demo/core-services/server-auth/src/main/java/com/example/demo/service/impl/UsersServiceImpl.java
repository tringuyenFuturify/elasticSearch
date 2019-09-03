package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;
import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

  private final UsersRepository usersRepository;

  public UsersServiceImpl(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  private List<Users> findAllBycriterias(Users criterias) {
    Stream<Users> users = StreamSupport.stream(usersRepository.findAll().spliterator(), false);

    if (criterias.getEmail() != null && criterias.getPassword() != null) {
      users = users.filter(i -> i.getEmail().equalsIgnoreCase(criterias.getEmail())
          && i.getPassword().equalsIgnoreCase(criterias.getPassword()) && i.getActive());
    }

    return users.collect(Collectors.toList());
  }

  @Override
  public Users signIn(String email, String password) {
    return findAllBycriterias(Users.builder().email(email).password(password).build()).stream()
        .findFirst().orElse(null);
  }

}
