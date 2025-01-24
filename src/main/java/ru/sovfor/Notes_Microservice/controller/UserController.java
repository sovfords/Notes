package ru.sovfor.Notes_Microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sovfor.Notes_Microservice.domain.Note;
import ru.sovfor.Notes_Microservice.domain.User;
import ru.sovfor.Notes_Microservice.service.NoteService;
import ru.sovfor.Notes_Microservice.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    long num;
    @Autowired
    private UserService userService;
    @Autowired
    private NoteService noteService;

    @GetMapping("/get")
    public String getUser( Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(authentication.getName());
        List<Note> publicNotes = noteService.getAllPublicNotes(user.getId());
        model.addAttribute("user",user);
        model.addAttribute("notesNotEmpty",!publicNotes.isEmpty());
        model.addAttribute("publicNotes",publicNotes);
        return "gettingUser";

    }

    @GetMapping("/get/{id}")
    public String getUserById(@PathVariable Long id ,Model model){
        User user = userService.findUserById(id);
        List<Note> publicNotes = noteService.getAllPublicNotes(user.getId());
        model.addAttribute("user",user);
        model.addAttribute("notesNotEmpty",!publicNotes.isEmpty());
        model.addAttribute("publicNotes",publicNotes);
        return "gettingUser";

    }



    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        num = id;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user",userService.getUserByName(authentication.getName()));
        return "userUpdatingForm";
    }
    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user , Model model){
        userService.updateUser(user,num);

        return "completeUserUpdating";
    }

    @GetMapping("/find")
    public String searchUser(Model model){
        model.addAttribute("name","");
        return "searchUserForm";
    }
    @PostMapping("/find")
    public String completeSearchUser(@RequestParam String name,Model model){
        model.addAttribute("user",userService.getUserByName(name));
        return "resultOfSearchingUser";
    }
}
