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
@RequestMapping("/notes")
public class NoteController {
    private long num;

    @Autowired
    UserService userService;
    @Autowired
    private NoteService noteService;

    @GetMapping("/create")
    public String getFormOfCreating(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("note", new Note(userService.getUserByName(authentication.getName())));
        return "creatingForm";
    }


    @PostMapping("/create")
    public String createNote(@ModelAttribute Note note, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        note.setUser(userService.getUserByName(authentication.getName()));
        noteService.createNote(note);
        model.addAttribute("name", note.getName());
        return "resultOfCreating";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        num = id;
        model.addAttribute("note", noteService.getNoteById(id));
        return "updatingNoteForm";
    }

    @PostMapping("/update")
    public String updateNote(@ModelAttribute Note note, Model model){

        noteService.updateNote(note, num);
        model.addAttribute("name", note.getName());
        return "resultOfUpdating";
    }


    @GetMapping()
    public String getAllNotes(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Note> notes = userService.getUserByName(authentication.getName()).getNotes();
        model.addAttribute("notes",notes);
        model.addAttribute("notesNotEmpty",!notes.isEmpty());
        return "notesMainPage";
    }

    @GetMapping("/get/{id}")
    public String getNoteById(@PathVariable long id, Model model){
        model.addAttribute("note",noteService.getNoteById(id));
        return "gettingNoteById";
    }

    @GetMapping("/public/get/{id}")
    public String publicGetNoteById(@PathVariable long id, Model model){
        model.addAttribute("note",noteService.getNoteById(id));
        return "publicGettingNoteById";
    }

    @GetMapping("/delete/{id}")
    public String deleteNoteById(@PathVariable long id, Model model){
        model.addAttribute("name",noteService.getNoteById(id).getName());
        noteService.deleteById(id);
        return "resultOfDeletingById";
    }

    @GetMapping("/delete/all")
    public String getFormOfDeletingAll(Model model){
        return "deletingAllForm";
    }

    @PostMapping("/delete/all")
    public String deleteAllNote( Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        noteService.deleteAllWithUserId(userService.getUserByName(authentication.getName()).getId());
        return "resultOfDeletingAll";
    }

}
