package sparta.BoardService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.BoardService.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
