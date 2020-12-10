package pl.coderslab.blinddate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.blinddate.dto.UserDto;
import pl.coderslab.blinddate.entity.User;
import pl.coderslab.blinddate.entity.UserDetails;
import pl.coderslab.blinddate.exception.DuplicateEmailException;
import pl.coderslab.blinddate.service.MessageService;
import pl.coderslab.blinddate.service.UserDetailsService;
import pl.coderslab.blinddate.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;

@Controller
@RequestMapping("")
@Slf4j
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final MessageService messageService;

    @GetMapping("")
    public String index(){
        return "/index/index";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "/index/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model){
        try{
            userService.checkEmailDuplicate(user);

        }catch (DuplicateEmailException e) {
            result.reject("Konto z takim adresem email już istnieje");
        }
        if(result.hasErrors()){
            return "index/register";
        }
        log.warn("Adding user with email " + user.getEmail());
        userService.save(user);
        return "index/index";
    }

    @GetMapping("/faq")
    public String faq(){
        return "/index/faq";
    }

    @GetMapping("/contact")
    public String contact(){
        return "/index/contact";
    }

    @GetMapping("/welcome")
    public String welcome(Model model,HttpServletResponse resp) throws IOException {
        if(userService.getLogged().isWithDetails()){
            resp.sendRedirect("/dashboard");
        }
        model.addAttribute("details", new UserDetails());
        return "/index/welcome";
    }

    @PostMapping("/welcome")
    @Transactional
    @Modifying
    public void saveDetails(@ModelAttribute("details") UserDetails details, HttpServletResponse resp) throws IOException {
        User logged = userService.getLogged();
        details.setUser(logged);
        userDetailsService.save(details);
        logged.setWithDetails(true);
        logged.setUserDetails(details);
        messageService.welcomeMessage(logged);
        resp.sendRedirect("/dashboard");
    }
}
