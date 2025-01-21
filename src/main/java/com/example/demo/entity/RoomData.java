package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int roomNumber;

    @Column(nullable = false)
    private float temperature;

    @Column(nullable = false)
    private float humidity;

    @Column(nullable = false)
    private float gasAmount;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public RoomData(int roomNumber, float temperature, float humidity, float gasAmount, LocalDateTime timestamp) {
        this.roomNumber = roomNumber;
        this.temperature = temperature;
        this.humidity = humidity;
        this.gasAmount = gasAmount;
        this.timestamp = timestamp;
    }
}
