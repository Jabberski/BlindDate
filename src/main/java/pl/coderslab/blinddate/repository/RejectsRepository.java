package pl.coderslab.blinddate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.blinddate.entity.Rejects;

public interface RejectsRepository extends JpaRepository<Rejects, Long> {
}
