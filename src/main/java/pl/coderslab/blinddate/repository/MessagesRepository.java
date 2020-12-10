package pl.coderslab.blinddate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import pl.coderslab.blinddate.entity.Messages;
import pl.coderslab.blinddate.entity.User;

import javax.transaction.Transactional;
import java.util.List;

public interface MessagesRepository extends JpaRepository<Messages, Long> {
    List<Messages> findAllByUser(User user);
    @Modifying
    @Transactional
    void deleteById(Long id);
}
