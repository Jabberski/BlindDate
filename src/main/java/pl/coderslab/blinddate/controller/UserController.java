package pl.coderslab.blinddate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.jpa.repository.Modifying;
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
import pl.coderslab.blinddate.service.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collections;
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
    private final UserDetailsService userDetailsService;


    @GetMapping("/calendar")
    public String getCalendar(Model model){
        boolean[][] calendar = calendarService.formCalendar();
        model.addAttribute("user",userService.getLogged());
        model.addAttribute("calendar", calendar);
        return "/user/calendar";
    }

    @PostMapping("/calendar")
    public void updateCalendar(HttpServletResponse resp, HttpServletRequest req) throws IOException {
        if(req.getParameterMap().containsKey("available")) {
            String[] available = req.getParameterValues("available");
            calendarService.saveCalendarChanges(available);
        } else {
            String[] available = new String[0];
            calendarService.saveCalendarChanges(available);
        }
        resp.sendRedirect("/user/calendar");
    }

    @GetMapping("/dates")
    public String getDates(Model model){
        User loggedUser = userService.getLogged();
        dateService.getAllDates(loggedUser.getId());
        model.addAttribute("user",loggedUser);
        model.addAttribute("dates", dateService.getAllDates(loggedUser.getId()));
        return "/user/dates";
    }

    @GetMapping("/messages")
    public String getMessages(Model model){
        User loggedUser = userService.getLogged();
        List<Messages> allMessages = messageService.getAllMessages(loggedUser);
        Collections.reverse(allMessages);
        model.addAttribute("user",loggedUser);
        model.addAttribute("messages", allMessages);
        return "/user/messages";
    }

    @PostMapping("/messages/details")
    @Transactional
    @Modifying
    public String messageDetails(@RequestParam Long id, Model model){
        messageService.getMessage(id).setSeen(true);
        model.addAttribute("user",userService.getLogged());
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
        model.addAttribute("user",loggedUser);
        model.addAttribute("details", loggedUser.getUserDetails());
        return "/user/details";
    }

    @PostMapping("/details")
    public void saveDetailsChanges(@ModelAttribute("details") UserDetails user, HttpServletResponse response) throws IOException {
        userDetailsService.updateUserDetails(user);
        response.sendRedirect("/dashboard");
    }

    @PostMapping("/view")
    public String viewUserDetail(Long id, Model model){
        model.addAttribute("user",userService.getLogged());
        userService.getById(id);
        model.addAttribute("details", userService.findById(id).getUserDetails());
        if(userService.findById(id).getUserDetails().getGender()=='F'){
            model.addAttribute("gender", "Kobieta");
        } else {
            model.addAttribute("gender", "Mężczyzna");
        }
        return "/user/view";
    }


}
