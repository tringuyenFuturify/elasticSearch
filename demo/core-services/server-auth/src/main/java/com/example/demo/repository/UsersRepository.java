package com.example.demo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.example.demo.model.Users;

public interface UsersRepository extends CrudRepository<Users, Integer> {

  @Query(nativeQuery = true,
      value = "SELECT * FROM users WHERE email = :email AND password = :pass")
  Optional<Users> signIn(@Param("email") String email, @Param("pass") String pass);
  
  Optional<Users> findByEmail(String email);

}
