package sparta.BoardService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.BoardService.entity.Board;


public interface BoardRepository extends JpaRepository<Board, Long> {
}
