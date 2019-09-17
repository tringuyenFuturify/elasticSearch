package io.futurify.authentication.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import io.futurify.authentication.model.Role;
import io.futurify.authentication.repository.RoleRepository;
import io.futurify.authentication.service.RoleService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  @Override
  public Page<Role> findAll(Pageable pageable) {
    return roleRepository.findAll(pageable);
  }

}
