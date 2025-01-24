package ru.sovfor.Notes_Microservice.domain;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    private String name;
    private String text;
    private Boolean isPublic = false;
    private LocalDateTime dateOfCreating;

    public Note(User user, String name, String text, boolean isPublic) {
        this.user = user;
        this.name = name;
        this.text = text;
        this.isPublic = isPublic;
        this.dateOfCreating = LocalDateTime.now();
    }

    public Note(User user){
        this.user = user;
        dateOfCreating = LocalDateTime.now();

    }

    public Note() {

        dateOfCreating = LocalDateTime.now();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public LocalDateTime getDateOfCreating() {
        return dateOfCreating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public void setDateOfCreating(LocalDateTime dateOfCreating) {
        this.dateOfCreating = dateOfCreating;
    }
}
