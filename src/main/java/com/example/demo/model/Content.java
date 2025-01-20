package com.example.demo.model;

import java.time.LocalDateTime;

public record Content (
    Integer id,
    String name,
    LocalDateTime time,
    Boolean in_out

) {


}



