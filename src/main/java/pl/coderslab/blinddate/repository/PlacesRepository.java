package pl.coderslab.blinddate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.blinddate.entity.Places;

import java.util.List;

public interface PlacesRepository extends JpaRepository<Places, Long> {
    List<Places> getAllByCity(String city);
}
