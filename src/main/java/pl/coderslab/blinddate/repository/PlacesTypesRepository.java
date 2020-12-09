package pl.coderslab.blinddate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.blinddate.entity.PlaceTypes;

public interface PlacesTypesRepository extends JpaRepository<PlaceTypes, Long> {
    PlaceTypes getByType(String type);
}
