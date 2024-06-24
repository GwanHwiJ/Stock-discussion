package sparta.UserService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.UserService.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
