package pl.coderslab.blinddate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.blinddate.entity.AvailableHours;
import pl.coderslab.blinddate.entity.User;
import pl.coderslab.blinddate.repository.AvailableHoursRepository;
import pl.coderslab.blinddate.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarServiceImpl implements CalendarService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AvailableHoursRepository availableHoursRepository;
    private final DateService dateService;

    Comparator<AvailableHours> compareByDayOfWeek = Comparator.comparingInt(AvailableHours::getDayOfWeek);


    @Override
    public boolean[][] formCalendar() {
        log.warn("forming calendar");
        User loggedUser = userService.getLogged();
        boolean[][] table = new boolean[11][7];
        List<AvailableHours> calendar = loggedUser.getAvailableHours();
        Collections.sort(calendar, compareByDayOfWeek);
        for (int i = 12; i < 23; i++) {
            for (int j = 1; j <= 7; j++) {
                for (AvailableHours a : calendar) {
                    if(a.getDayOfWeek()==j && a.getHour()==i){
                        table[i-12][j-1] = true;
                        calendar.remove(a);
                        break;
                    }
                }
            }
        }
        return table;
    }

    @Override
    @Transactional
    public void saveCalendarChanges(String[] available) {
        User loggedUser = userService.getUserByEmail(userService.getLoggedEmail());
        log.warn("Saving calendar changes for user "+ loggedUser.getId());
        availableHoursRepository.deleteAllByUser(loggedUser);
        for(String record : available){
            String[] parts = record.split(" ");
            AvailableHours availableHours = new AvailableHours(loggedUser,Integer.parseInt(parts[1]), Integer.parseInt(parts[0]) );
            availableHoursRepository.save(availableHours);
        }
        dateService.lookForAvailableDates(loggedUser);
    }


}
