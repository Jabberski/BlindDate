package pl.coderslab.blinddate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.blinddate.entity.AvailableHours;
import pl.coderslab.blinddate.entity.User;
import pl.coderslab.blinddate.service.CalendarService;
import pl.coderslab.blinddate.service.DateService;
import pl.coderslab.blinddate.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CalendarService calendarService;
    private final DateService dateService;

    @GetMapping("/calendar")
    public String getCalendar(Model model){
        boolean[][] calendar = calendarService.formCalendar();
        model.addAttribute("calendar", calendar);
        return "/user/calendar";
    }

    @PostMapping("/calendar")
    public void updateCalendar(@RequestParam(value = "available") String[] available, HttpServletResponse resp) throws IOException {
        calendarService.saveCalendarChanges(available);
        resp.sendRedirect("/user/calendar");
    }

    @GetMapping("/dates")
    public String getDates(Model model){
        User loggedUser = userService.getUserByEmail(userService.getLoggedEmail());
        model.addAttribute("dates", dateService.getAllDates(loggedUser.getId()));
        return "/user/dates";
    }


}
