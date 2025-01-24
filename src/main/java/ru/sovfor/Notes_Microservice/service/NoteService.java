package ru.sovfor.Notes_Microservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sovfor.Notes_Microservice.domain.Note;
import ru.sovfor.Notes_Microservice.domain.User;
import ru.sovfor.Notes_Microservice.repository.NoteRepository;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository repository;

    public Note createNote(Note note){
       return repository.save(note);
    }

    public Note getNoteById(Long id){
        return repository.findById(id).orElse(null);
    }
    public void deleteById(Long id){
        repository.deleteById(id);
    }
    public List<Note> getAllNotes(){
        return repository.findAll();
    }

    public Note updateNote(Note note,long id){

        Note oldNote = repository.findById(id).orElse(null);
        oldNote.setName(note.getName());
        oldNote.setIsPublic(note.getIsPublic());
        oldNote.setDateOfCreating(note.getDateOfCreating());
        oldNote.setText(note.getText());
       return repository.save(oldNote);
    }
    @Transactional
    public void deleteAllWithUserId(Long id){
        repository.deleteAllNotesWithUserId(id);
    }

    public List<Note> getAllPublicNotes(Long id){
        return repository.getAllPublicNotes(id);
    }

}
