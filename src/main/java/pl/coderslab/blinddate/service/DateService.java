package pl.coderslab.blinddate.service;

import pl.coderslab.blinddate.entity.AvailableHours;
import pl.coderslab.blinddate.entity.Dates;
import pl.coderslab.blinddate.entity.User;

import java.util.List;

public interface DateService {
    void createNewDate(User user1, User user2);
    List<Dates> getAllDates(Long id);
    AvailableHours lookForSuitableDateTime(User user1, User user2);
}
