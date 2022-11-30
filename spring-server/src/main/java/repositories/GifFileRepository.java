package repositories;

import org.springframework.data.repository.CrudRepository;

import domain.GifFile;

public interface GifFileRepository extends CrudRepository<GifFile, Long> {
}

