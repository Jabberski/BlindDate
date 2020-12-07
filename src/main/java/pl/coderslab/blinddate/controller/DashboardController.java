package pl.coderslab.blinddate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.blinddate.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/dashboard")
@Slf4j
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("available", userService.findAvailableForUser());
        return "/dashboard/dashboard";
    }

    @GetMapping("/like/{id}")
    public void like(@PathVariable Long id, HttpServletResponse resp) throws IOException {
        userService.likeUser(id);
        resp.sendRedirect("/dashboard");
    }
    @GetMapping("/reject/{id}")
    public void reject(@PathVariable Long id, HttpServletResponse resp) throws IOException {
        userService.rejectUser(id);
        resp.sendRedirect("/dashboard");
    }


}
