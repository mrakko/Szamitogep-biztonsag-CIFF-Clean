package com.example.ciffclean.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.ciffclean.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
