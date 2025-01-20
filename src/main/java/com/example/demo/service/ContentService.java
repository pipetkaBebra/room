package com.example.demo.service;

import com.example.demo.model.Content;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContentService {

    private final List<Content> contentList = new ArrayList<>();

    public boolean toggleBooleanById(Integer id) {
        for (int i = 0; i < contentList.size(); i++) {
            Content content = contentList.get(i);
            if (content.id().equals(id)) {
                // Создаём новый объект с противоположным значением Boolean
                Content updatedContent = new Content(
                        content.id(),
                        content.name(),
                        content.time(),
                        !content.in_out()
                );
                // Заменяем старый объект на новый
                contentList.set(i, updatedContent);
                return true;
            }
        }
        return false; // Если объект с указанным ID не найден
    }

    // Метод для добавления тестовых данных
    @PostConstruct
    private void init() {
        contentList.add(new Content(1, "Test Content 1", LocalDateTime.now(), true));
        contentList.add(new Content(2, "Test Content 2", LocalDateTime.now(), false));
    }
}


