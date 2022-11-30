package io.swagger.repositories;

import org.springframework.data.repository.CrudRepository;

import io.swagger.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
