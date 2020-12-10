package pl.coderslab.blinddate.service;

import pl.coderslab.blinddate.entity.AvailableHours;

import java.util.List;

public interface CalendarService {

    boolean[][] formCalendar();
    void saveCalendarChanges(String[] available);
}
