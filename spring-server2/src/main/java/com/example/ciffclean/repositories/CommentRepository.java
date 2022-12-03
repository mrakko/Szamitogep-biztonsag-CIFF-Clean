package com.example.ciffclean.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.ciffclean.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
