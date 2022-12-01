package com.example.ciffclean.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.ciffclean.domain.GifFile;

public interface GifFileRepository extends CrudRepository<GifFile, Long> {
    @Query("SELECT gf FROM GifFile gf WHERE gf.name LIKE CONCAT('%',:text,'%')")
    List<GifFile> findByNameContains(String text);
}

