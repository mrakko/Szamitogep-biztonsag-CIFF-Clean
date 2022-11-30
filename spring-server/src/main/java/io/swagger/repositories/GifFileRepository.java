package io.swagger.repositories;

import org.springframework.data.repository.CrudRepository;

import io.swagger.domain.GifFile;

public interface GifFileRepository extends CrudRepository<GifFile, Long> {
}

