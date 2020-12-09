package pl.coderslab.blinddate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.blinddate.entity.Messages;

public interface MessagesRepository extends JpaRepository<Messages, Long> {
}
