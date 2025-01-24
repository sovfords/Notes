package ru.sovfor.Notes_Microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sovfor.Notes_Microservice.domain.Note;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
    @Modifying
    @Query(value = "DELETE FROM notes WHERE user_id = ?1",nativeQuery = true)
    void deleteAllNotesWithUserId(Long id);
    @Query(value = "SELECT * FROM notes WHERE user_id = ?1 AND is_public = 1",nativeQuery = true)
    List<Note> getAllPublicNotes(Long id);
}
