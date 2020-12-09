package pl.coderslab.blinddate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.blinddate.dto.UserDto;
import pl.coderslab.blinddate.entity.AvailableHours;
import pl.coderslab.blinddate.entity.Likes;
import pl.coderslab.blinddate.entity.Rejects;
import pl.coderslab.blinddate.entity.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User getByEmail(String email);

    User findFirstByEmail(String email);

    Optional<User> findByEmail(String email);

    List<User> findAllByCity(String city);

    @Query("SELECT u.likes FROM User u WHERE u.id = :userId")
    List<Likes> findLiked(@Param("userId") Long id);

    @Query("SELECT u.rejected FROM User u WHERE u.id = :userId")
    List<Rejects> findRejected(@Param("userId") Long id);

    @Query("SELECT u.availableHours FROM User u WHERE u.id = :userId")
    List<AvailableHours> getCalendar(@Param("userId") Long id);

}
