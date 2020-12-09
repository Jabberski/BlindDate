package pl.coderslab.blinddate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.blinddate.entity.Likes;

public interface LikesRepository extends JpaRepository<Likes, Long> {
}
