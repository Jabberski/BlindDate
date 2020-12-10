package pl.coderslab.blinddate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.blinddate.dto.UserDto;
import pl.coderslab.blinddate.entity.AvailableHours;
import pl.coderslab.blinddate.entity.Messages;
import pl.coderslab.blinddate.entity.User;
import pl.coderslab.blinddate.entity.UserDetails;
import pl.coderslab.blinddate.repository.UserDetailsRepository;
import pl.coderslab.blinddate.service.CalendarService;
import pl.coderslab.blinddate.service.DateService;
import pl.coderslab.blinddate.service.MessageService;
import pl.coderslab.blinddate.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
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
    private final MessageService messageService;
    private final UserDetailsRepository userDetailsRepository;

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
        User loggedUser = userService.getLogged();
        model.addAttribute("dates", dateService.getAllDates(loggedUser.getId()));
        return "/user/dates";
    }

    @GetMapping("/messages")
    public String getMessages(Model model){
        User loggedUser = userService.getLogged();
        model.addAttribute("messages", messageService.getAllMessages(loggedUser));
        return "/user/messages";
    }

    @PostMapping("/messages/details")
    public String messageDetails(@RequestParam Long id, Model model){
        model.addAttribute("message", messageService.getMessage(id));
        return "/user/messageDetails";
    }

    @PostMapping("/messages/delete")
    public void deleteMessage(@RequestParam Long id, HttpServletResponse resp) throws IOException {
        messageService.deleteMessage(id);
        resp.sendRedirect("/user/messages");
    }

    @GetMapping("/details")
    public String editDetails(Model model){
        User loggedUser = userService.getLogged();
        model.addAttribute("details", loggedUser.getUserDetails());
        return "/user/details";
    }

    @PostMapping("/details")
    public void saveDetailsChanges(@ModelAttribute("details") UserDetails user, HttpServletResponse response) throws IOException {
        userDetailsRepository.updateUserDetails(user.getAge(), user.getDescription(), user.getGender(),
                user.getName(), user.getOrientation(), user.getUser());
        response.sendRedirect("/dashboard");
    }


}
