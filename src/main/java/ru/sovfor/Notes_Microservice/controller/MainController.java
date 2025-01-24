package ru.sovfor.Notes_Microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sovfor.Notes_Microservice.domain.User;
import ru.sovfor.Notes_Microservice.repository.UserRepository;
import ru.sovfor.Notes_Microservice.service.UserService;


@Controller
public class MainController {

    @Autowired
    UserService userService;

//    @GetMapping()
//    public String mainPage(){
//        return "mainPage";
//    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user",new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String completeRegistration(@ModelAttribute User user, Model model){
        boolean userIsAdded = false;
        if(userService.getUserByName(user.getNickname()) == null){
            userIsAdded = userService.saveUser(user);
        }

        model.addAttribute("userIsAdded", userIsAdded);
        model.addAttribute("name",user.getNickname());
        return "completeUserSaving";

    }

    @GetMapping("/main")
    public String mainPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("name",authentication.getName());
        return "mainPage";

    }


}

