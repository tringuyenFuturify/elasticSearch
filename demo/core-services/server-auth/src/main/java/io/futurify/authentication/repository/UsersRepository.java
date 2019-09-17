package io.futurify.authentication.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import io.futurify.authentication.model.User;

public interface UsersRepository extends CrudRepository<User, Integer> {

  @Query(nativeQuery = true,
      value = "SELECT * FROM user WHERE email = :email AND password = :pass")
  Optional<User> signIn(@Param("email") String email, @Param("pass") String pass);
  
  Optional<User> findByEmail(String email);

}
