package com.example.ciffclean.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.ciffclean.domain.AppUser;

public interface UserRepository extends CrudRepository<AppUser, Long> {
}
