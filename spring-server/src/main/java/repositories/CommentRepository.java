package repositories;

import org.springframework.data.repository.CrudRepository;

import domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
