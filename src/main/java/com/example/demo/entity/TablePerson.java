package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TablePerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private boolean isActive;
    private LocalDateTime createdAt;

    private String roomNumber; // Нове поле для номера аудиторії

    public TablePerson(String name, String email, boolean isActive, LocalDateTime createdAt, String roomNumber) {
        this.name = name;
        this.email = email;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.roomNumber = roomNumber;
    }

    private LocalDateTime updatedAt;

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getActiveSymbol() {
        return isActive ? "+" : "-";
    }
}
