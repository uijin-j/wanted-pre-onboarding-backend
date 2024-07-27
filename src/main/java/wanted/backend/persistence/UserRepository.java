package wanted.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.backend.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
