package com.example.demo.repository;

import com.example.demo.entity.RoomData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomDataRepository extends JpaRepository<RoomData, Long> {

    List<RoomData> findByRoomNumber(int roomNumber);  // повертаємо список, а не один елемент



    RoomData findByRoomNumberAndTemperatureAndHumidityAndGasAmount(int roomNumber, float temperature, float humidity, float gasAmount);
}
