package pl.coderslab.blinddate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.blinddate.dto.UserDto;
import pl.coderslab.blinddate.entity.Likes;
import pl.coderslab.blinddate.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User getByEmail(String email);

    User findFirstByEmail(String email);

    Optional<User> findByEmail(String email);

//    @Query(value = "SELECT * FROM users WHERE city = ?1",
//            nativeQuery = true)
    List<User> findAllByCity( String city);

    @Query("SELECT u.likes FROM User u WHERE u.id = :userId")
    List<Likes> findLiked(@Param("userId") Long id);
}
