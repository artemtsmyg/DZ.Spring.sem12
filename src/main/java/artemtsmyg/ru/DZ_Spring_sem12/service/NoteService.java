package artemtsmyg.ru.DZ_Spring_sem12.service;

import artemtsmyg.ru.DZ_Spring_sem12.model.Note;
import artemtsmyg.ru.DZ_Spring_sem12.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с заметками.
 */
@Service
@AllArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    /**
     * Добавляет новую заметку.
     * @param note Заметка для добавления.
     * @return Добавленная заметка.
     */
    public Note addNote(Note note) {
        return noteRepository.save(note);
    }
    /**
     * Получает список всех заметок.
     * @return Список всех заметок.
     */
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }
    /**
     * Получает заметку по идентификатору.
     *
     * @param id Идентификатор заметки.
     * @return Найденная заметка.
     */
    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElseThrow(null);
    }
    /**
     * Обновляет информацию о заметке.
     * @param note Новая информация о заметке.
     * @return Обновленная заметка.
     */
    public Note updateNote(Note note) {
        Note noteById = getNoteById(note.getId());
        noteById.setDescription(note.getDescription());
        noteById.setTitle(note.getTitle());
        noteById.setLocal_date_time(LocalDateTime.now());
        return noteRepository.save(noteById);
    }
    /**
     * Удаляет заметку по идентификатору.
     * @param id Идентификатор заметки.
     */
    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }
    /**
     * Обновляет статус заметки.
     * @param id Идентификатор заметки.
     * @param status Новый статус заметки.
     * @return Обновленная заметка с новым статусом.
     */
    public Note updateNoteStatus(Long id, Note.Status status) {
        Optional<Note> optionalTask = noteRepository.findById(id);
        if (optionalTask.isPresent()) {
            Note task = optionalTask.get();
            task.setStatus(status);
            return noteRepository.save(task);
        } else {
            throw new IllegalArgumentException("Task not found with id: " + id);
        }
    }
    /**
     * Получает список заметок по статусу.
     *
     * @param status Статус заметок для поиска.
     * @return Список заметок с указанным статусом.
     */
    public List<Note> getNoteByStatus(Note.Status status) {
        return noteRepository.getNoteByStatus(status);
    }
}
