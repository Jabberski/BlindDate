package pl.coderslab.blinddate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.blinddate.dto.UserDto;
import pl.coderslab.blinddate.entity.User;
import pl.coderslab.blinddate.exception.DuplicateEmailException;
import pl.coderslab.blinddate.service.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;

@Controller
@RequestMapping("")
@Slf4j
public class IndexController {

    private final UserService userService;

    @Autowired
    public IndexController(UserService userService){
        this.userService=userService;
    }

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

}
