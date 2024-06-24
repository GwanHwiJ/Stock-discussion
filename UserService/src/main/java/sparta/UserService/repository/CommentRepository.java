package sparta.UserService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.UserService.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
