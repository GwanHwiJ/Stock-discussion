package sparta.BoardService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.BoardService.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
