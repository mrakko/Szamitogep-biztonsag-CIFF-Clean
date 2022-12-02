package com.example.ciffclean.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.ciffclean.domain.GifFile;

public interface GifFileRepository extends CrudRepository<GifFile, Long> {
    @Query("SELECT gf FROM GifFile gf WHERE gf.name LIKE CONCAT('%',:text,'%')")
    List<GifFile> findByNameContains(String text);

    @Query("SELECT gf FROM GifFile gf left join fetch gf.comments WHERE gf.id = :id")
    Optional<GifFile> findByIdWithComments(Long id);

    //@Query("SELECT gf.content FROM GifFile gf WHERE gf.id = :id")
    //Optional<byte[]> findByIdWithByteContent(Long id);
}

