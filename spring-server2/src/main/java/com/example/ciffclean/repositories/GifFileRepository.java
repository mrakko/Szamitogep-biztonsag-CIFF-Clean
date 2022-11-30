package com.example.ciffclean.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.ciffclean.domain.GifFile;

public interface GifFileRepository extends CrudRepository<GifFile, Long> {
}

