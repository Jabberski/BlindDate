package pl.coderslab.blinddate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.blinddate.entity.*;
import pl.coderslab.blinddate.repository.DateRepostiory;
import pl.coderslab.blinddate.repository.PlacesRepository;
import pl.coderslab.blinddate.repository.PlacesTypesRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class DateServiceImpl implements DateService {

    private final DateRepostiory dateRepostiory;
    private final MessageService messageService;
    private final PlacesRepository placesRepository;
    private final PlacesTypesRepository placesTypesRepository;


    @Override
    public void createNewDate(User user1, User user2, Matches match) {
        log.warn("Creating new date with users " + user1.getId() + " and " + user2.getId());
        AvailableHours availableHours = lookForSuitableDateTime(user1, user2);
        if (availableHours == null) {
            messageService.noSuitableTimeMessage(user1, user2);
        } else {
            match.setDateArranged(true);
            Dates newDate = new Dates();
            newDate.setUser1(user1);
            newDate.setUser2(user2);
            newDate.setDateTime(convertAvailableHour(availableHours));
            newDate.setPlace(getPlaceForDate(match));
            dateRepostiory.save(newDate);
            messageService.dateMessage(newDate);
        }
    }

    @Override
    public List<Dates> getAllDates(Long id) {
        log.warn("Getting all dates for user " + id);
        List<Dates> allByUser1Id = dateRepostiory.findAllByUser1Id(id);
        List<Dates> allByUser2Id = dateRepostiory.findAllByUser2Id(id);
        List<Dates> allDates = List.copyOf(allByUser1Id);
        for (Dates d : allByUser2Id) {
            allDates.add(d);
        }
        return allDates;
    }

    @Override
    public AvailableHours lookForSuitableDateTime(User user1, User user2) {
        log.warn("Searching for suitable date time for users " + user1.getId() + " and " + user2.getId());
        List<AvailableHours> availableHoursForUser1 = user1.getAvailableHours();
        List<AvailableHours> availableHoursForUser2 = user2.getAvailableHours();
        for (AvailableHours a1 : availableHoursForUser1) {
            for (AvailableHours a2 : availableHoursForUser2) {
                if (a1.getDayOfWeek() == a2.getDayOfWeek() && a1.getHour() == a2.getHour()) {
                    return a1;
                }
            }
        }
        return null;
    }

    @Override
    public LocalDateTime convertAvailableHour(AvailableHours availableHours) {
        log.info("converting AvailableHours to LocalDateTime");
        DayOfWeek dayOfWeek;
        switch (availableHours.getDayOfWeek()) {
            case 1:
                dayOfWeek = DayOfWeek.MONDAY;
                break;
            case 2:
                dayOfWeek = DayOfWeek.TUESDAY;
                break;
            case 3:
                dayOfWeek = DayOfWeek.WEDNESDAY;
                break;
            case 4:
                dayOfWeek = DayOfWeek.THURSDAY;
                break;
            case 5:
                dayOfWeek = DayOfWeek.FRIDAY;
                break;
            case 6:
                dayOfWeek = DayOfWeek.SATURDAY;
                break;
            default:
                dayOfWeek = DayOfWeek.SUNDAY;
                break;
        }
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.with(TemporalAdjusters.next(dayOfWeek)).withMinute(0).withSecond(0).withHour(availableHours.getHour());
    }

    @Override
    public Places getPlaceForDate(Matches match) {
        log.warn("Getting place for date");
        List<Places> allByCity = placesRepository.getAllByCity(match.getUser1().getCity());
        PlaceTypes compatiblePlaceType = getCompatiblePlaceType(match);
        List<Places> compatiblePlace = new ArrayList<>();
        for(Places place : allByCity){
            if(place.getType().equals(compatiblePlaceType)){
                compatiblePlace.add(place);
            }
        }
        if(compatiblePlace.size()>0){
            Random random = new Random();
            int i = random.nextInt(compatiblePlace.size());
            return compatiblePlace.get(i);
        }
        return null;
    }

    @Override
    public PlaceTypes getCompatiblePlaceType(Matches match) {
        log.warn("Looking for compatible place type");
        List<PlaceTypes> preferredByUser1 = match.getUser1().getUserDetails().getPreferredPlaceTypes();
        List<PlaceTypes> preferredByUser2 = match.getUser2().getUserDetails().getPreferredPlaceTypes();
        List<PlaceTypes> compatibleTypes = new ArrayList<>();
        for (PlaceTypes type : preferredByUser1) {
            if (preferredByUser2.contains(type)) {
                compatibleTypes.add(type);
            }
        }
        if(compatibleTypes.size()>0){
            Random random = new Random();
            int i = random.nextInt(compatibleTypes.size());
            return compatibleTypes.get(i);
        }
        return placesTypesRepository.getByType("Pub");
    }


}
