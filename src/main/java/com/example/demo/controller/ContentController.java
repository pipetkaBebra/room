package com.example.demo.controller;

import com.example.demo.model.Content;
import com.example.demo.repository.ContentCollectionRepository;
import com.example.demo.service.ContentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collector;

@RestController
@RequestMapping("/api/content")

public class ContentController {

    private ContentCollectionRepository repository;
    private final ContentService contentService;

    public ContentController(ContentCollectionRepository repository, ContentService contentService){

        this.repository = repository;
        this.contentService = contentService;
    }


    @GetMapping("")
    public List<Content> findAll(){
        return repository.findAll();
    }

    @PostMapping("/{id}/toggle")

    public ResponseEntity<String> toggleBoolean(@PathVariable Integer id) {
        boolean updated = contentService.toggleBooleanById(id);
        if (updated) {
            return ResponseEntity.ok("Значение Boolean успешно обновлено.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Объект с указанным ID не найден.");
        }
    }

    @PostMapping("/api/content/upload")
    public ResponseEntity<String> receiveTable(@RequestBody List<Content> table) {
        table.forEach(content -> System.out.println(content));
        return ResponseEntity.ok("Таблиця отримана успішно!");
    }


}
