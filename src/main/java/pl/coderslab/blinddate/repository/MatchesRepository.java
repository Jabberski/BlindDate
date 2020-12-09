package pl.coderslab.blinddate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.blinddate.entity.Matches;
import pl.coderslab.blinddate.entity.User;

import java.util.List;

public interface MatchesRepository extends JpaRepository<Matches, Long> {
    List<Matches> findAllByUser1(User user);
    List<Matches> findAllByUser2(User user);
}
