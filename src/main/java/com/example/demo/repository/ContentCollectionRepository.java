package com.example.demo.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Content;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;


@Repository
public class ContentCollectionRepository {

    private final List<Content> content = new ArrayList<>();

    public ContentCollectionRepository(){
    }

    public List<Content> findAll(){return content;}


    public List<Content> findAllById(Boolean in_out) {
        return content.stream()
                .filter(c -> c.in_out().equals(in_out))
                .collect(Collectors.toList());
    }


    @PostConstruct
    private void init(){

        Content c = new Content(1, "Brilla Kailler", LocalDateTime.now(), true);
        Content c2 = new Content(2, "Meilor Kailler", null, false);

        content.add(c);
        content.add(c2);


    }




}
