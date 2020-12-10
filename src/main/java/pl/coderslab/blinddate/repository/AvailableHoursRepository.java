package pl.coderslab.blinddate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import pl.coderslab.blinddate.entity.AvailableHours;
import pl.coderslab.blinddate.entity.User;

import javax.transaction.Transactional;

public interface AvailableHoursRepository extends JpaRepository<AvailableHours, Long> {
    @Modifying
    @Transactional
    void deleteAllByUser(User user);
}
