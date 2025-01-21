package com.example.demo.service;

import com.example.demo.entity.RoomData;
import com.example.demo.entity.TablePerson;
import com.example.demo.repository.RoomDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomDataService {

    private final RoomDataRepository roomDataRepository;

    @Autowired
    public RoomDataService(RoomDataRepository roomDataRepository) {
        this.roomDataRepository = roomDataRepository;
    }

    // Зберігаємо дані про кімнату
    public void saveRoomData(int roomNumber, float temperature, float humidity, float gasAmount) {
        RoomData roomData = new RoomData(roomNumber, temperature, humidity, gasAmount, LocalDateTime.now());
        roomDataRepository.save(roomData);
    }

    // Отримуємо всі дані про кімнати
    public List<RoomData> getAllRoomData() {
        return roomDataRepository.findAll();
    }

    // Отримуємо дані по номеру кімнати
    public List<RoomData> getRoomDataByRoomNumber(int roomNumber) {
        return roomDataRepository.findByRoomNumber(roomNumber);  // повертаємо список
    }

    // Отримуємо дані по параметрах
    public RoomData getRoomDataByParams(int roomNumber, float temperature, float humidity, float gasAmount) {
        return roomDataRepository.findByRoomNumberAndTemperatureAndHumidityAndGasAmount(roomNumber, temperature, humidity, gasAmount);
    }

    public void saveRoomData1(int roomNumber, float temperature, float humidity, float gasAmount) {
        RoomData roomData = new RoomData();
        roomData.setRoomNumber(roomNumber);
        roomData.setTemperature(temperature);
        roomData.setHumidity(humidity);
        roomData.setGasAmount(gasAmount);
        roomData.setTimestamp(LocalDateTime.now()); // Установка поточного часу

        roomDataRepository.save(roomData);
    }

}
