package pl.coderslab.blinddate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.blinddate.entity.Dates;
import pl.coderslab.blinddate.entity.User;

import java.util.List;

public interface DateRepostiory extends JpaRepository<Dates, Long> {
    List<Dates> findAllByUser1Id(Long id);
    List<Dates> findAllByUser2Id(Long id);

}
