package com.example.ciffclean.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.ciffclean.domain.Log;

public interface LogRepository extends CrudRepository<Log, Long>{
    
}
