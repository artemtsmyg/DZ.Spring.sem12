package artemtsmyg.ru.DZ_Spring_sem12.controller;

import artemtsmyg.ru.DZ_Spring_sem12.model.Note;
import artemtsmyg.ru.DZ_Spring_sem12.service.FileGateAway;
import artemtsmyg.ru.DZ_Spring_sem12.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * NoteController - класс обрабатывающий входящие запросы и возвращают ответы по заметкам.
 */
@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    private final FileGateAway fileGateAway;
    /**
     * Получить список всех заметок
     * @return список всех заметок
     */
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        return new ResponseEntity<>(noteService.getAllNotes(), HttpStatus.OK);
    }
    /**
     * Создать новую заметку
     * @param note данные новой заметки
     * @return созданная заметка
     */
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {

        fileGateAway.writeToFile(note.getTitle() + ".txt", note.toString());

        return new ResponseEntity<>(noteService.addNote(note), HttpStatus.CREATED);
    }
    /**
     * Получить заметку по идентификатору
     * @param id идентификатор заметки
     * @return найденная заметка или пустая заметка, если не найдена
     */
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") Long id) {
        Note noteById;
        try {
            noteById = noteService.getNoteById(id);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Note());
        }
        return new ResponseEntity<>(noteById, HttpStatus.OK);
    }
    /**
     * Обновить статус заметки
     * @param id идентификатор заметки
     * @param status новый статус заметки
     * @return обновленная заметка
     */
    @PutMapping("/{id}")
    public Note updateNoteStatus(@PathVariable Long id, @RequestBody Note.Status status) {
        return noteService.updateNoteStatus(id, status);
    }
    /**
     * Получить список заметок по статусу
     * @param status статус заметки
     * @return список заметок с указанным статусом
     */
    @GetMapping("/status/{status}")
    public List<Note> getNoteByStatus(@PathVariable Note.Status status) {
        return noteService.getNoteByStatus(status);
    }
    /**
     * Обновить данные заметки
     * @param note новые данные заметки
     * @return обновленная заметка
     */
    @PutMapping
    public ResponseEntity<Note> updateNote(@RequestBody Note note) {
        return new ResponseEntity<>(noteService.updateNote(note), HttpStatus.OK);
    }
    /**
     * Удалить заметку по идентификатору
     * @param id идентификатор заметки
     * @return статус успешного удаления
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable("id") Long id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.ok().build();
    }
}
