package io.swagger.repositories;

import org.springframework.data.repository.CrudRepository;

import io.swagger.domain.Auth;

public interface AuthRepository extends CrudRepository<Auth, Long> {
    
}
