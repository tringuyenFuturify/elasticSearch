package io.futurify.authentication.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import io.futurify.authentication.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

  Page<Role> findAll(Pageable pageRequest);

}
