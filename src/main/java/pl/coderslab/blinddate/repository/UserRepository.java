package pl.coderslab.blinddate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.blinddate.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User getByEmail(String email);

    User findFirstByEmail(String email);

    Optional<User> findByEmail(String email);
}
