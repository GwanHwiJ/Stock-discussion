package sparta.UserService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.UserService.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
