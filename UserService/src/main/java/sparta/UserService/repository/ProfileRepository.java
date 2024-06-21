package sparta.UserService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.UserService.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
