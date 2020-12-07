package pl.coderslab.blinddate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.blinddate.entity.AvailableHours;
import pl.coderslab.blinddate.entity.User;
import pl.coderslab.blinddate.repository.UserRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarServiceImpl implements CalendarService {

    private final UserService userService;
    private final UserRepository userRepository;

    Comparator<AvailableHours> compareByDayOfWeek = Comparator.comparingInt(AvailableHours::getDayOfWeek);

    @Override
    public List<AvailableHours> getCalendar() {
        User loggedUser = userService.getUserByEmail(userService.getLoggedEmail());
        return userRepository.getCalendar(loggedUser.getId());
    }

    @Override
    public boolean[][] formCalendar() {
        boolean[][] table = new boolean[24][7];
        List<AvailableHours> calendar = getCalendar();
        Collections.sort(calendar, compareByDayOfWeek);
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 7; j++) {
                for (AvailableHours a : calendar) {
                    if(a.getDayOfWeek()==j+1 && a.getHour()==i){
                        table[i][j] = true;
                        calendar.remove(a);
                        break;
                    }
                    if(a.getDayOfWeek()>i+1) break;
                }
            }
        }
        return table;
    }
}
