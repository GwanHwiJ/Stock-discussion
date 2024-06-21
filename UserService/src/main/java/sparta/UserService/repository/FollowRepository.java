package sparta.UserService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.UserService.entity.Follow;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
