package io.futurify.authentication.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import io.futurify.authentication.model.Role;

public interface RoleService {

  public Page<Role> findAll(Pageable pageable);
  
}
