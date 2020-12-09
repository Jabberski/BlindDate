package pl.coderslab.blinddate.service;

import pl.coderslab.blinddate.entity.*;

import java.time.LocalDateTime;
import java.util.List;

public interface DateService {
    void createNewDate(User user1, User user2, Matches match);
    List<Dates> getAllDates(Long id);
    AvailableHours lookForSuitableDateTime(User user1, User user2);
    LocalDateTime convertAvailableHour(AvailableHours availableHours);
    Places getPlaceForDate(Matches match);
    PlaceTypes getCompatiblePlaceType(Matches match);
}
