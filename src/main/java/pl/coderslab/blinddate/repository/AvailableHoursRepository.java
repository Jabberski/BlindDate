package pl.coderslab.blinddate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.blinddate.entity.AvailableHours;
import pl.coderslab.blinddate.entity.User;

public interface AvailableHoursRepository extends JpaRepository<AvailableHours, Long> {
    void deleteAllByUser(User user);
}
