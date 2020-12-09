package pl.coderslab.blinddate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.blinddate.entity.AvailableHours;
import pl.coderslab.blinddate.entity.Dates;
import pl.coderslab.blinddate.entity.User;
import pl.coderslab.blinddate.repository.DateRepostiory;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DateServiceImpl implements DateService{

    private final DateRepostiory dateRepostiory;


    @Override
    public void createNewDate(User user1, User user2) {
        log.warn("Creating new date with users "+ user1.getId()+ " and " +  user2.getId());
        AvailableHours availableHours = lookForSuitableDateTime(user1, user2);
        if(availableHours==null){

        }else {
            Dates newDate = new Dates();
            newDate.setUser1(user1);
            newDate.setUser2(user2);
            dateRepostiory.save(newDate);
        }
    }

    @Override
    public List<Dates> getAllDates(Long id) {
        log.warn("Getting all dates for user "+ id);
        List<Dates> allByUser1Id = dateRepostiory.findAllByUser1Id(id);
        List<Dates> allByUser2Id = dateRepostiory.findAllByUser2Id(id);
        List<Dates> allDates = List.copyOf(allByUser1Id);
        for(Dates d : allByUser2Id){
            allDates.add(d);
        }
        return allDates;
    }

    @Override
    public AvailableHours lookForSuitableDateTime(User user1, User user2) {
        log.warn("Searching for suitable date time for users "+  user1.getId()+ " and " +  user2.getId());
        List<AvailableHours> availableHoursForUser1 = user1.getAvailableHours();
        List<AvailableHours> availableHoursForUser2 = user2.getAvailableHours();
        for(AvailableHours a1 : availableHoursForUser1){
            for(AvailableHours a2 : availableHoursForUser2){
                if(a1.getDayOfWeek()==a2.getDayOfWeek()&&a1.getHour()==a2.getHour()){
                    return  a1;
                }
            }
        }
        return null;
    }


}
