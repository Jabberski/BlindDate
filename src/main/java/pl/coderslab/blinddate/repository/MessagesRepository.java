package pl.coderslab.blinddate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.blinddate.entity.Messages;
import pl.coderslab.blinddate.entity.User;

import java.util.List;

public interface MessagesRepository extends JpaRepository<Messages, Long> {
    List<Messages> findAllByUser(User user);
    void deleteById(Long id);
}
