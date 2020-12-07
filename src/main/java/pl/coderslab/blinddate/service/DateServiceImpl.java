package pl.coderslab.blinddate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.blinddate.entity.Dates;
import pl.coderslab.blinddate.entity.User;
import pl.coderslab.blinddate.repository.DateRepostiory;
import pl.coderslab.blinddate.repository.UserRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DateServiceImpl implements DateService{

    private final EntityManager entityManager;
    private final DateRepostiory dateRepostiory;


    @Override
    public void createNewDate(Long id1, Long id2) {
        entityManager.createNativeQuery("INSERT INTO dates (user1id, user2id) VALUES (?,?)")
                .setParameter(1, id1)
                .setParameter(2, id2)
                .executeUpdate();
    }

    @Override
    public List<Dates> getAllDates(Long id) {
        List<Dates> allByUser1Id = dateRepostiory.findAllByUser1Id(id);
        List<Dates> allByUser2Id = dateRepostiory.findAllByUser2Id(id);
        List<Dates> allDates = List.copyOf(allByUser1Id);
        for(Dates d : allByUser2Id){
            allDates.add(d);
        }
        return allDates;
    }


}
