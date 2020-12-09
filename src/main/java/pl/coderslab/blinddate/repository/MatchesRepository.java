package pl.coderslab.blinddate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.blinddate.entity.Matches;

public interface MatchesRepository extends JpaRepository<Matches, Long> {
}
