package com.example.note.controller;

import com.example.note.exception.ResourceNotFoundException;
import com.example.note.model.Note;
import com.example.note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    // 获取笔记列表
    @GetMapping
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // 新建笔记
    @PostMapping
    public Note createNote(@Valid @RequestBody Note note) {
        return noteRepository.save(note);
    }

    // 获取笔记
    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable("id") Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("笔记", "ID", noteId));
    }

    // 更新笔记
    @PutMapping("/{id}")
    public Note updateNote(@PathVariable("id") Long noteId, @Valid @RequestBody Note noteDetail) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("笔记", "ID", noteId));

        note.setTitle(noteDetail.getTitle());
        note.setContent(noteDetail.getContent());

        return noteRepository.save(note);
    }

    // 删除笔记
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable("id") Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("笔记", "ID", noteId));

        noteRepository.delete(note);

        return ResponseEntity.ok().build();
    }
}
