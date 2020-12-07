package pl.coderslab.blinddate.service;

import pl.coderslab.blinddate.entity.Dates;

import java.util.List;

public interface DateService {
    void createNewDate(Long id1, Long id2);
    List<Dates> getAllDates(Long id);
}
